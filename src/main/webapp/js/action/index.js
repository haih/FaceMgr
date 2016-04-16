//动作主目录,包含默认动作,beforeAjax和afterAjax
define(function(require,exports,module){
	var $ = require('jquery') ;
	var util = require('utils/index') ;

	//exports对外提供所有动作接口
	//公共模块,所有模块公用的最基础函数
	$.extend(true,exports,require('action/common/common')) ;


	//人脸库 模块
	$.extend(true,exports,require('action/faceLibrary/before')) ;
	$.extend(true,exports,require('action/faceLibrary/after')) ;

	//主题管理 模块
	$.extend(true,exports,require('action/themeManage/before')) ;
	$.extend(true,exports,require('action/themeManage/after')) ;

	//主页通用 模块
	$.extend(true,exports,require('action/index/after')) ;
	$.extend(true,exports,require('action/index/before')) ;



})