//公共函数
define(function(require,exports,module){
	
	var $ = require('jquery');
	var util = require('utils/index');
	var view=require('utils/index')
	
    // 关闭dialog 方法
    exports.closeCommonDilaog=function(){
    	util.getCommonDialog().close();
        return {
            isStop : true 
        }
    }
    
});