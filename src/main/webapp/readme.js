////基于sea.js模块化开发的单页面web应用
//注意,这里使用sea.js2.0 因为支持shim插件不需要自主封装jquery等插件
//1.配置
//seajs.config({
//	base : {
//
//	},
//	alias : {
//
//	}
//})
//	base-基础路径,其余的路劲配置都在其基础之上,默认值是sea.js的上一层
//	alias-资源别名配置
//	paths-文件路径配置
//	vars-加载文件时的变量
//	map-尾部后缀映射,便于调试
//	preload-老浏览器一些东西预加载
//	debug-调试模式
//	charset-文件编码/可为函数
//
//2.模块标识
//	相对标识-以.或..开头;出现在define的factory中;永远相对于当前js的URL解析
//	顶级标识-不以.或..或/开始;相对基础路径base来解析
//	普通路径-除了以上都是;相对于当前html页面解析;seajs.use中作为入口始终为普通路径
//
//3.CMD模块定义规范
//define('hello', ['jquery'], function(require, exports, module) {
//  // 模块代码
//});
//	define多个参数-依次是模块标识,依赖,和表示内部代码的工厂函数
//	define单个参数-可以是对象;字符串;或工厂函数
//		工厂函数的require参数-
//			该方法只接受模块标识获取接口
//				var a = require('./a');
//				a.dosomething();
//			require.async其可以内部异步加载模块,之后回调
//				require.async(['./c','./d'],function(c,d){
//					c.dosomething();
//					d.dosomething();
//				})	
//			reqiure.resolve解析路径返回绝对路径,不加载
//		工厂函数的exports参数,为一个对象,向外提供模块接口
//			exports.foo = 'bar' ;
//			exports.dosomething = function(){}
//			或直接
//			return {
//				foo : 'bar',
//				dosomething : function(){}
//			};
//			若define模块只有return,可以省去return
//		工厂函数的module参数是一个对象,存储了当前模块相关的一些属性和方法
//			module.id-模块唯一标识
//			module.uri-解析所得的模块结对路径
//			module.dependencise-当前模块依赖
//			module.exports-当前模块对外提供的接口(exports执行不了的他可以搞)赋值需同步
//
//4.require书写约定
//	工厂函数第一个参数 必须 名为require
//	不要重命名require
//	require参数必须是字符串直接量, 不能拼接
//
//5.模块的加载启动
//	<script>
//		seajs.use('./main');
//	</script>
//	可加载任意模块,或一次加载多个模块,回调可选
//	seajs.use(['./a','./b'],function(a,b){	
//		a.init();
//		b.init();
//	});
//	只用于加载启动,不应出现在define中的模块代码中,那里用require.async方法
//
//
//6.seajs的插件(和版本有关,有的集成进去了)
//	seajs-css 用来加载css,类似加载<link>标签;或一部分代码
//	seajs-preload 配置时可预加载
//	seajs-text 用来加载非js的文本,是XHR实现
//		var tpl = require('./a.tpl');
//		可使用.tpl或.html后缀或text!前缀来指明文本文件
//		可使用.json后缀或json!前缀来指明JSON文件
//	seajs-style 加载样式,创建一个style元素,设置内容然后插入
//		seajs.impirtStyle('body {margin:0}');
//	seajs-combo HTTP连续请求,需配合服务器,自动对同一批次的多个模块合并下载
//	seajs-flush 连续请求,减少HTTP请求,合适的时候一并触发use的下载
//	seajs-debug 线上调试
//		使用alias在config中添加依赖
//			alias: {"seajs-debug" : "../seajs-debug" }
//		在URL中添加seajs-debug参数,访问后右下角有浮层
//	seajs-log 
//		如果浏览器不支持console(说的就是你,IE)
//		if (typeof console === 'undefined') {
//		    console = { log: function() {}, warn: function() {}, ... }
//		}
//		为的是上线后，如果某个浏览器不支持 console ，代码不会抛错。
//		那么可以用seajs.log搞定
//			seajs.log('My' , 'Lily');
//	seajs-health 检测多版本共存的问题
//
//
//
//
////基于seajs的模块化开发单页面app思路
//app.js的统一路由处理
//	1.<a>标签click触发,根据<a>标签参数data-XXX决定操作,一般无需表单数据
//		不存在src=""则不触发请求与跳转,可以自定义简单事件
//		存在src=""则需要有请求,只有src无data属性则为普通请求,否则为ajax请求
//		存在src="defaultEvent",阻止后续事件,停止操作
//		*存在src="autoEvent",不阻止后续事件,触发常规操作
//		有data-beforeAjax=""属性,在ajax前会进行处理
//		有data-afterAjax=""属性,在ajax后会进行处理
//
//	2.<form>标签统一处理,根据<form>标签参数data-XXX决定操作,一般和表单数据打交道
//		不存在action,则这根本就是错的吧
//		存在action不存在data-XXX属性,则普通submit
//		有data-beforeAjax=""属性,在ajax前会进行处理
//		有data-afterAjax=""属性,在ajax后会进行处理
//	
//	3.<form>初始化时绑定validate,监听init事件
//		当form有data-validate=""的属性的时候,其加载进page时主动触发init事件
//
//	4.<a>和<form>带有confirm="请确认是否要删除/登出"属性的元素,点击之后会优先重定向, 就是会先触发确认弹窗, 弹窗中的确认键才是继续执行操作, 取消则返回. 
//
//	5.<a>标签中带有data-autoevent的值, 则默认执行<a>标签默认事件.
//
//
