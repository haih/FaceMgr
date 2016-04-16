//工具集
//全局ajax
define(function(require,exports,module){
	//jquery
	var $ = require('jquery') ;
	//弹框插件
	var dialog = require('dialog') ;
	//模板插件
	var juicer = require('juicer') ;


	//1.全局ajax工具函数
	//接受三个参数,url地址,params参数,success回调,是否预处理,要传递的参数类型
	exports.ajax = function(url,params,success,processDate,contentType){
		var _this = this ;

		//时间戳
		if (url.indexOf('?')>=0){
			//url有?号则在其后加&t参数
			url = url + "&t=" + new Date().valueOf() ;
 		}else{
 			//没有?号就在后面加?t参数
 			url = url + "?t=" + new Date().valueOf() ;
 		}
 		//isProcessDate默认为false
 		var isProcessDate = (processDate===false)?false : true ;
 		//contentType默认为参数
 		var contentType = (contentType)?contentType : "application/x-www-form-urlencoded" ;

 		//请求ajax处理
 		$.ajax({
 			url:url, //目标地址
 			data:params, //post参数
 			type:'POST', //POST
 			dataType:'json', //期望返回json
 			contentType: contentType, //设置contentType 'application/json'
 			// processDate: isProcessDate,
 			//contentType: contentType, //设置contentType 'application/json'
 			processDate: isProcessDate,
 			beforeSend:function(xhr){
 				//发送前处理,开启ajax遮罩

 				//尝试获取唯一的aioloaddingdialog的ID
 				var loadingDialog = dialog.get('aioLoaddingdialog');
 				//如果dialog不存在
 				if (!loadingDialog){
 					loadingDialog = dialog({id:'aioLoaddingdialog',title:'',content:'<img src="./img/loading.gif" style="width:70px;heigth:70px;border-radius:3px;"/>',width:70,height:70,skin:'dialog-wating',fixed:true})
 					// 添加计算ajax 请求的数量
					loadingDialog.ajaxCount=0;
 				}
 				// 处理延迟关闭的问题，
				clearTimeout(loadingDialog.lazyid);
				// 统计请求次数
				loadingDialog.ajaxCount=loadingDialog.ajaxCount+1
 				//该dialog未打开,则展示,否则不用展示
 				if (!loadingDialog.open){
 					loadingDialog.showModal() ;
 				}
 			},
 			success:function(data,status,xhr){
		 				try{
		 					//返回的标记成功与否的参数status为false时,return
		 					if (data.status === false) {
		 						//如果弹出层已存在,需要插一个进弹出层里面
		 						var commonDialog = dialog.get('aioDialog') ;
		 						var $tipsWrap = undefined ;
		 						if (commonDialog&&commonDialog.open){
		 							//查找dialog中的title
		 							var $dialogTitle = $(commonDialog.node).find('.tabs,.title');
		 							//为tipsWrap指定下一个wrap
		 							var $tipsWrap = $dialogTitle.next('.tips-wrap');
		 							//没有获取到
		 							if ($tipsWrap.length === 0){
		 								//新建一个tips-wrap
		 								$tipsWrap = $('<div class="tips-wrap"></div>').insertAfter($dialogTitle) ;
		 							}
		 						}

		 						//展示错误信息,取自message或key参数
		 						_this.showTip('error',data.message||data.key,$tipsWrap);
		 						return ;
		 					};
		 					//成功后执行回调
		 					success.apply(_this,arguments) ;
		 				}catch(e){
		 					if(typeof window.console !== 'undefined'){
		 						console.warn('please contact web developer! qzguo,phone:18110908730');
		 						console.error(e) ;
		 					}
		 				}
 					},
 			error:function(xhr,status,error){
	 				var sessionstatus=xhr.getResponseHeader("sessionstatus") ;
	 				//如果session超时
	 				if (sessionstatus==="timeout") {
	 					return ;
	 				};
	 				var commonDialog = dialog.get('aioDialog');
	 				var $tipsWrap = undefined ; 
	 				if (commonDialog && commonDialog.open){
	 					var $dialogTitle = $(commonDialog.node).find('.tabs,.title') ;
	 					var $tipsWrap = $dialogTitle.next('.tips-wrap') ;
	 					if ($tipsWrap.length === 0) {
	 						$tipsWrap = $('<div class="tips-wrap"></div>').insertAfter($dialogTitle) ;
	 					};
	 				}
	 				if (new RegExp('^40*').test(xhr.status)) {
	 					//40X让他算404相关错误
	 					_this.showTip('error','请求资源不存在，或者文件已删除！',$tipsWrap)
	 				}else if(new RegExp('^50*').test(xhr.status)){
	 					//50X让他算50X相关服务器错误
	 					_this.showTip('error','服务器异常，请稍后再试!',$tipsWrap);
	 				}else if(status === 'timeout'){
	 					_this.showTip('error','请求超时，请稍后再试!',$tipsWrap);
					}else{
						_this.showTip('error','未知错误',$tipsWrap);
					}	
	 			},
 			complete:function(xhr,status){
		 				//完成ajax操作后需要关闭遮罩层
		 				var loadingDialog = dialog.get('aioLoaddingdialog') ;
		 				loadingDialog.ajaxCount=loadingDialog.ajaxCount-1;
						
						if( loadingDialog.ajaxCount ===0){
							// 延迟200 毫秒, 防止出现刚关闭有显示的问题
							loadingDialog.lazyid=setTimeout(function(){
								loadingDialog.close();
							},200);
						}
						//检测是否超时
						var sessionstatus=xhr.getResponseHeader("sessionstatus"); 
			          	if(sessionstatus=="timeout"){ 
			        	   //如果超时就处理 ，指定要跳转的页面  
				           window.location.href = ctx + "/logout.do";
			         	}   

		 				
		 			}

 		})

	}

	//2.消息提示工具函数
	//提示消息的展示,接受三个参数,展示消息类型,展示内容,包裹层
	exports.showTip = function(type,msg,$tipsWrap){
		if ($.type($tipsWrap) === 'undefined'){
			$tipsWrap = $('.tips-wrap');
			//检测tips-wrap是否滚出可视区域
			$(window).scrollTop() > 56 ? $tipsWrap.addClass('fixed') : $tipsWrap.removeClass('fixed') ;
		}
		//清空当前tips
		$tipsWrap.empty();

		//tips模板内部,根据type来定义css样式
		//wrap一直存在,内容持续变化
		var $tips = $('<div class="tip '+type+'-tip">'+msg+'</div>').appendTo($tipsWrap) ;
		//重新定位tips的位置,使其全屏居中
		$tips.css({'margin-left':'-'+($tips.outerWidth()/2)+'px',left:'50%','z-index':2});
		//2秒后清除tips
		setTimeout(function(){
			$tips.hide().remove();
		},3000);
	}
	//2.1 针对左侧导航栏的提示
	//展示导航提示方式, 要么是seccess或error,string是提示语,wrap是dom对象,是提示语要覆盖的区域
	exports.showNavTips = function(type,string,wrap){
		var width = $(wrap).width() ;
		var height = $(wrap).height() ;

		var tipEle = '<div class="navTips" style="position:absolute;left:0;top:0;border-radius:4px;width:'+width+'px;height:'+height+'px;text-align:center;line-height:'+height+'px;">'+string+'</div>' ;
		// if (!($(wrap).css('position') === 'absolute' || $(wrap).css('position') === 'relative')){
		// 	$(wrap).css({'position':'relative'}) ;
		// }

		//需要全局定位, 不依赖于已存在的wrap, 之前的wrap定位方法全部注释掉
		//获取wrap偏移值
		var top = $(wrap).offset().top ; 
		var left = $(wrap).offset().left ;

		// $(wrap).append(tipEle) ;
		// if (type === 'success') {
		// 	$('.navTips').css({'color':'#fff','background':'#6ebe6c'});
		// }else{
		// 	$('.navTips').css({'color': '#fff','background-color': '#DB5757'});
		// };
		// setTimeout(function(){
		// 	$('.navTips').remove() ;
		// },3000)

		$(document.body).append(tipEle) ;
		if (type === 'success') {
			$('.navTips').css({'color':'#fff','background':'#6ebe6c','left':left+1,'top':top+1});
		}else{
			$('.navTips').css({'color': '#fff','background-color': '#DB5757','left':left+1,'top':top+1});
		};
		setTimeout(function(){
			$('.navTips').remove() ;
		},3000)

	}


	//3.获取当前dialog,如有重置后返回,否则新建一个返回
	exports.getCommonDialog = function(){
		//获取系统统一的那个普通dialog
		var commonDialog = dialog.get('aioDialog') ;
		//若此dialog不存在,新建一个
		if (!commonDialog){
			commonDialog = dialog({id:'aioDialog',title:'',content:'',fixed:true});	
		}
		//重置文本框
		commonDialog.width(465).title('').height('');
		return commonDialog ;
	}

	//4.form表单数组转化为对象形式
	exports.formDataArrTranstoObj = function(arrParams){
		var resultParams = {} ;
		$.each(arrParams,function(i,n){
			transformData.call(resultParams,n.name,n.value) ;
		});
		return resultParams ;
	}

	//5.input在内容不为空的时候出现一个删除按钮,点击之后清空当前input并为空搜索一次
	exports.inputDelete = function($input){
		if ($input.hasClass('faceNameInput')){
			//icon选择
			var iconStr = 'icon-clearInput' ;
		}else if($input.hasClass('faceLibraryInput')){
			//icon选择
			var iconStr = 'icon-clearInputInFaceDialog' ;
		}else if($input.hasClass('faceLibraryInput')){
			var iconStr = 'icon-clearInputFaceLibrary' ;
		}

		var icon = '<i class="icon '+iconStr+'"></i>' ;

		//事件绑定,keydown事件绑定,keydown完毕后如果输入框为空则×号消失,否则出现,并为该×号绑定点击事件
		if ($input.val===''){
			$('i.'+iconStr+'').remove() ;
		}else{
			if ($('i.'+iconStr+'').size()===0){
				$input.after(icon) ;
				//绑定该icon事件
				$('i.'+iconStr+'').on('click',function(event){
					$input.val('') ;
					$('i.'+iconStr+'').parent().find('a').trigger('click') ;
					$('i.'+iconStr+'').remove() ;
					$input.focus() ;
				});
			}			
			
		}

	}




	var standNameExp = /^[_0-9a-zA-Z]+$/i ;
	//工具辅助函数,将每个传入的键与值成为一个对象
	function transformData(key,value){
		//值为空就不转化其数值
		if (!value || value === '') return ; 

		//判断key是不是最简单的情况
		if(standNameExp.test(key)){
			if (typeof this[key] === 'undefined'){
				this[key] = value ;
			}else if(typeof this[key] === 'string'){
				//判断该属性是否已赋值,我们要把这个键值对变成一个数组
				var newValue = [] ;
				newValue.push(this[key]) ;
				newValue.push(value) ;
				this[key] = newValue ; 
			}else{
				//说明已经是一个数组,往后追加字符就行
				this[key].push(value) ;
			}
			return ;
		}
		//  处理时不时直接是一个数组的情况
		var arrayNameExp=/(.*)\[([0-9]*)\]$/i;
		if(arrayNameExp.test(key)){
			// 运算表达式
			arrayNameExp.exec(key);
			// 获取数组的 key 和index
			var arrayKey=RegExp.$1,arrayIndex=parseInt(RegExp.$2)
			if(typeof this[arrayKey] ==='undefined'){
				this[arrayKey]=[];
			}
			if(typeof arrayIndex === 'undefined' || arrayIndex === '' || isNaN(arrayIndex)){
				this[arrayKey].push(value);
			}else {
				// 一路检测，直到指定位置天蝎内容
				for(var i=0;i<arrayIndex;i++){
					if(typeof this[arrayKey][i]==='undefined'){
						this[arrayKey][i]='';
					}				
				}
				this[arrayKey][arrayIndex]=value;
			}
			return;
		}
		// 处理多层级情况
		var index=key.indexOf('.'),firstKey=key.substr(0,index);
		if(standNameExp.test(firstKey)){
			if(typeof this[firstKey] === 'undefined'){
				this[firstKey]={};
			}
			arguments.callee.call(this[firstKey],key.substr(index+1),value);
			return ;
		}	
		arrayNameExp.exec(firstKey);
		var keyName=RegExp.$1,arrIndex=parseInt(RegExp.$2);
		if(typeof this[keyName] === 'undefined'){
			this[keyName]=[];
		}
		var curArr=this[keyName];
		for(var i=0;i<=arrIndex;i++){
			if(typeof this[keyName][i]==='undefined'){
				this[keyName][i]={};
			}
		}
		arguments.callee.call(this[keyName][arrIndex],key.substr(index+1),value);
	}

})