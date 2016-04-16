//视图控制模块
define(function(require,exports,module){
	var $ = require('jquery') ;
	var juicer = require('juicer') ;
	var util = require('utils/index') ;
	//判断元素是否为html的dom
	var isDOM = ( typeof HTMLElement === 'object' ) ?
                function(obj){
                    return obj instanceof HTMLElement;
                } :
                function(obj){
                    return obj && typeof obj === 'object' && obj.nodeType === 1 && typeof obj.nodeName === 'string';
                }
	//模板渲染工具函数
	//参数1.父元素, 为'dialog'字符就弹窗,为string则选选择,为dom或$元素你懂
	//参数2.渲染模板, 
	//参数3.模板携带参数
	//参数4.回调函数
	exports.display = function($wrap,tpl,data,callback){
		//如果数据为空,置为空对象
		if (!data) data={};

		//如果回调方法为空,置为空函数
		if(!callback) callback=function(){};

		//数据渲染
		require.async(['juicer','utils/viewHelper'],function(juicer){
			var isDialog ;
			if ($wrap === 'dialog'){
				isDialog = 'dialog' ;
				//如果传入对话框字符串,获取dialog
				var dialog = util.getCommonDialog() ; 
				//为dialog渲染参数
				dialog.content(juicer(tpl,data));
				//切换$wrap至该dialog内容区域,方便后续该区域内容的事件绑定
				$wrap = $('.ui-dialog-content') ;
			}else if(typeof $wrap === 'string' || isDOM($wrap)){
				//是选择器字符或dom对象,用jquery构造器包裹下咯
				$wrap = $($wrap).empty() ;
				$wrap.get(0).innerHTML = juicer(tpl,data) ;
			}else{
				$wrap.get(0).innerHTML = juicer(tpl,data) ;
			}

			//渲染完毕后,对新渲染部分的初始化
			//1.触发其中的表单初始化init事件
			$wrap.find('form').trigger('init') ;
			//2.触发不支持placeHolder的浏览器的自定义placeHolder事件
			$wrap.find('input[placeholder],text[placeholder]').each(function(){
				placeholder($(this)) ;
			}) ;


			//执行回调函数
			if(isDialog){
				//dialog的回调传弹窗本身和内容区两个参数
				callback(dialog,$wrap) ;
			}else{
				//否则只传一个原包裹层
				callback($wrap) ;
			}

		})
	}


	//增强函数
	//1.placeHolder不支持的浏览器上的增强
	function placeholder($this){
		//检测支持情况
		if ('placeholder' in document.createElement('input')){
			return ;
		}
		//计算当前输入框的位置和大小
		var width = $this.width() ; //宽
		var height = $this.height() ; //高
		var placeContent = $this.attr('placeholder') ; //值
		var randomId = Math.floor(Math.random()*1000) ; //随机ID

		//当前控件绑定属性值
		$this.data('placeholerId',randomId)

	}









})