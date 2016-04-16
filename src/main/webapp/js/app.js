//appjs , 请求监听 , 路由控制 , webapp初始化
define(function(require){
	//获取jquery对象
	var $ = require('jquery') ;
	//监听事件统一处理
	//事件一.
	//监听a标签[默认的]点击事件
	$(document).delegate('a','click',aClickListener) ;
	//事件二.
	//监听form表单[默认的]提交事件
	$(document).delegate('form','submit',formSubmitListener) ;
	//事件三.
	//监听form表单初始化[自定义]事件
	$(document).delegate('form','init',formInitListener) ;


	/*
	 *	A标签点击处理器,拦截A标签动作
	 *		data-beforeAjax = ''  
     *		data-afterAjax = ''
     *	    src = '' 
	 */
	function aClickListener(event){
		var event = $.event.fix(event || window.event);
	 	var $target = $(event.currentTarget) ;

	 	//获取Ajax请求前的操作函数名
	 	var beforeAjax = $target.data('beforeajax') ;
	 	//获取Ajax请求后的操作函数名
	 	var afterAjax = $target.data('afterajax') ; 
	 	//获取Ajax目标url值
	 	var url = $target.attr('href') ;
	 	//该元素是否要有确认弹窗操作,把这个confirm处理放到before操作之后,因为了能要过before的判断
	 	var confirmed = $target.data('confirm') ;

	 

	 	//如果herf不是有效路径,阻止跳转
	 	//url不存在,为空字符或有javascript与#,就是非法
	 	if (!url||url==''||new RegExp('^javascript$|^#$').test(url)){
	 		//阻止a默认事件
	 		event.preventDefault() ;
	 	}else if(url=='defaultEvent'){
	 		event.preventDefault() ;
	 		return ;
	 	}else if($target.data('autoevent')){	//默认操作名存在,执行默认操作去
	 		//执行默认事件autoEvent
	 		var autoAction = $target.data('autoevent') ;
	 		return true
	 	}else{
	 		//href为有效地址
	 		//阻止a默认事件
	 		event.preventDefault() ;
	 	}

	 	//过滤不合理事件,初步处理后进入主处理流程中,此时需要异步加载其他的文件
	 	require.async(['utils/index','action/index'],function(util,action){
	 		//获取元素的ajax之前的方法
	 		var beforeAjaxAction = action[beforeAjax]||function(){} ;
	 		//获取元素的ajax之后的方法
	 		var afterAjaxAction = action[afterAjax]||function(){} ;

	 		// url以text!开头时,是静态文件,直接渲染然后执行afterAjaxAction
	 		if(new RegExp('^text!.*').test(url)){
	 			util.showStaticPage(url.substr(5),function(tpl){
	 				afterAjaxAction.call(action,tpl,$target) ;
	 			})
	 			return ;
	 		}

	 		//执行beforeAjax处理函数,接受url和源a标签,返回处理后的url和params,以及是否要继续进行ajax请求
	 		var preOperation = beforeAjaxAction.call(action,url,$target) ;
	 		//处理后的url
	 		url = preOperation?preOperation.url : url ; 
	 		//处理后的params
	 		var params = preOperation?preOperation.params : {} ;
	 		//返回的对象参数中isStop为true时,停止后续动作,退出a标签事件	
	 		var isStop = preOperation?preOperation.isStop : false ;
	 		if (isStop === true){//确认为true,就是确认停止后续操作
	 			return ;
	 		}

	 		//元素confirm事件处理,如果这个元素需要点击确认,生成一个确认框,将当前target原封不动的放在确认键上,除去confirm属性
		 	if (confirmed){
		 		confirmed = {
		 			tips : confirmed 
		 		}
		 		require.async(['utils/view','utils/index'],function(view,util){
		 			//关闭对话框
					var dialog = util.getCommonDialog() ;
					if (dialog) {dialog.close() ;}
		 			var tpl = require('text!../template/confirm.htm') ;
		 			view.display('dialog',tpl,confirmed,function(dialog){
		 				if (dialog.open) {
             			   return;
            			}
            			//改变内容,删除其data-confirm属性
            			$clone = $target.clone().text('确定').data('confirm',null) ;	
            			/***/
            			//针对退出的额外处理
						if ($target.hasClass('logout')){
							$clone = $clone.removeClass('logout').data('autoevent','yes') ;	
						}
						/***/		
		 				dialog.showModal();
		 				//替换模板中确认内部的标签
		 				$('.dialog-form .confirmBtn').html($clone) ;			
		 			});
		 		});
		 		return ;
		 	}
		 	//全局ajax,最后两个参数,processDate代表是否要自动转换,contentType是传递的参数类型
	 		//特殊处理test,传图片且为多张图片,即为数组的时候
	 		// if ( afterAjax === 'addSubjectAfter' && ($.type(params.imgPath) === 'array') ){
 			if ( (afterAjax === 'uploadYourErrorMsg') ){
 				if ($.type(params.faceList)!=='array'){
	 				params.faceList = [params.faceList] ;
 				}

	 			params = JSON.stringify(params) ;
	 			util.ajax(url,params,function(data){
		 			//请求参数回放入请求结果中,参数名params

		 			$.extend(true,data,{params:params}) ;
		 			//执行afterAjax处理函数,一般接受两个参数,服务器返回值和源a标签
		 			afterAjaxAction.call(action,data,$target) ;
		 		},false,'application/json') ;

		 		return
	 		}
	 		//全局ajax
	 		util.ajax(url,params,function(data){
	 			//请求参数回放入请求结果中,参数名params
	 			$.extend(true,data,{params:params}) ;
	 			//执行afterAjax处理函数,一般接受两个参数,服务器返回值和源a标签
	 			afterAjaxAction.call(action,data,$target) ;
	 		})

	 	});//require.async
	 };//function aClickListener


	 /*
	 *	form表单提交事件处理器,拦截submit动作
	 *		data-beforeAjax = ''  
     *		data-afterAjax = ''
     *	    action = '' 
	 */
	 function formSubmitListener(event){
	 	//取得触发事件的元素
	 	var event = $.event.fix(event || window.event);
	 	var $target = $(event.currentTarget) ;

	 	//获取Ajax请求前的操作函数名
	 	var beforeAjax = $target.data('beforeajax') ;
	 	//获取Ajax请求后的操作函数名
	 	var afterAjax = $target.data('afterajax') ;

	 	event.preventDefault();//阻止默认跳转事件

	 	require.async(['utils/index','action/index','jquery.validate'],function(util,action){
	 		//验证表单是否有效,无效就返回
	 		if (!$target.valid()){
	 			return false ;
	 		}

	 		//获取元素的ajax之前的方法
	 		var beforeAjaxAction = action[beforeAjax]||function(){} ;
	 		//获取元素的ajax之后的方法
	 		var afterAjaxAction = action[afterAjax]||function(){} ;
	 		
	 		//获取Ajax目标url值
		 	var url = $target.attr('action') ;
		 	//初步序列表表单,目前的参数是N个键值对对象数组
		 	var params = $target.serializeArray() ;
		 	//二次序列化,变成一个N个参数的键值对对象
		 	params = util.formDataArrTranstoObj(params) ;

	 		//执行beforeAjax处理函数,接受url和源a标签,返回处理后的url和params,以及是否要继续进行ajax请求
	 		var preOperation = beforeAjaxAction.call(action,url,$target,params) ;
	 		//处理后的url
	 		url = preOperation?preOperation.url : url ; 
	 		//处理后的params
	 		var params = preOperation?preOperation.params : params ;
	 		//返回的对象参数中isStop为true时,停止后续动作,退出form标签事件	
	 		var isStop = preOperation?preOperation.isStop : false ;
	 		if (isStop){
	 			return ;
	 		}
	 		//全局ajax,最后两个参数,processDate代表是否要自动转换,contentType是传递的参数类型
	 		//特殊处理test,传图片且为多张图片,即为数组的时候
	 		// if ( afterAjax === 'addSubjectAfter' && ($.type(params.imgPath) === 'array') ){
 			if ( (afterAjax === 'addSubjectAfter' || afterAjax === 'addFaceAfter') ){
 				if ($.type(params.imgPath)!=='array'){
	 				params.imgPath = [params.imgPath] ;
 				}

	 			params = JSON.stringify(params) ;
	 			util.ajax(url,params,function(data){
		 			//请求参数回放入请求结果中,参数名params

		 			$.extend(true,data,{params:params}) ;
		 			//执行afterAjax处理函数,一般接受两个参数,服务器返回值和源a标签
		 			afterAjaxAction.call(action,data,$target) ;
		 		},false,'application/json') ;

		 		return
	 		}

	 		util.ajax(url,params,function(data){
	 			//请求参数回放入请求结果中,参数名params

	 			$.extend(true,data,{params:params}) ;
	 			//执行afterAjax处理函数,一般接受两个参数,服务器返回值和源a标签
	 			afterAjaxAction.call(action,data,$target) ;
	 		})


	 	})


	 };//function formSubmitListener(event)

	/*
	 *	form表单初始化事件处理器 
	 */
	 function formInitListener(event){
	 	//取得触发事件的元素
	 	var event = $.event.fix(event || window.event);
	 	var $target = $(event.currentTarget) ;
	 	//取得验证规则对象
	 	var validateName = $target.data('validate') ;
	 	//如果有验证规则,执行其规则的绑定
	 	if (validateName && validateName!==""){
	 		require.async(['utils/validate','jquery.validate','libs/form/adition-method'],function(validate){
	 			//为当前初始化的这个form绑定validate验证
	 			$target.validate(validate[validateName]) ;	
	 		})
	 	}	
	 };//function formInitListener(event)


	//页面初始化,页面初始启动
	// 系统启动
	require.async('action/index',function(action){
		// 重新设置页面标题
		document.title='人脸识别管理系统';
		$('#themeManage a').trigger('click') ;

		//顶部切换
		$('.navbar a').on('click',function(event){
			$('.navbar li').removeClass('selected') ;
			$(event.currentTarget).parent().addClass('selected') ;
		})
		//获取当前时间,来提示上午好下午好
		var hour = (new Date()).getHours() ;
		var text = "" ;
		if (hour>1 && hour<=5){
			text = "凌晨好" ;
		}else if(hour>5 && hour<=11){
			text = "上午好" ;
		}else if(hour>11 && hour<=13){
			text = "中午好" ;
		}else if(hour>13 && hour<=17){
			text = "下午好" ;
		}else if(hour>17 && hour<=19){
			text = "傍晚好" ;
		}else if(hour>19 && hour<=22){
			text = "晚上好" ;
		}else{
			text = "深夜好" ;
		}
		$('span.welcome').text(text+'！'+$('span.welcome').text()) ;
	});


})