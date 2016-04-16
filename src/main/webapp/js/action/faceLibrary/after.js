define(function(require,exports,module){
	var $ = require('jquery') ;
	var util = require('utils/index') ;
	var view = require('utils/view') ;

	//ajax后,展示右侧主题区域,点击人脸库后的操作
	exports.showFaceList = function(data){
		//关闭对话框
		var dialog = util.getCommonDialog() ;
		if (dialog) {dialog.close() ;}

		//获取模板
		var tpl = require('text!{tpls}/faceLibrary/bodyer.htm') ;

		view.display($('#bodyer'),tpl,data,function($wrap){
			//增加input Enter支持
			$('.faceLibraryInput').off('keydown') ;
			$('.faceLibraryInput').on('keydown',function(event){
				var $target = $(event.currentTarget) ;
				if ($target.val() === '') {return};
				if (event.keyCode === 13){
					$('.searchWrap a').trigger('click') ;
					return ;
				}
				//input绑定删除重置功能.
				util.inputDelete($(this)) ;
			})	

		})

	}



	//ajax后,关闭弹窗并提示成功
	exports.addFaceAfter = function(data){
		//操作成功!
		if (data.key === 'success.operation') {
			util.getCommonDialog().close();
			$('#faceLibrary a').trigger('click') ;
			
			util.showTip('success','添加人员信息成功!');
		}else{
			util.showTip('error','添加人员信息失败!');
		};

	}


	//搜索之后,处理函数
	exports.searchFaceAfter = function(data){
		//关闭对话框
		var dialog = util.getCommonDialog() ;
		if (dialog) {dialog.close();}

		//获取模板
		var tpl = require('text!{tpls}/faceLibrary/bodyer.htm') ;

		view.display($('#bodyer'),tpl,data,function($wrap){
			$('.faceLibraryInput').val(data.params.name);
			//增加input Enter支持
			$('.faceLibraryInput').off('keydown') ;
			$('.faceLibraryInput').on('keydown',function(event){
				var $target = $(event.currentTarget) ;
				if (event.keyCode === 13){
					$('.searchWrap a').trigger('click') ;
					return ;
				}
				//input绑定删除重置功能.
				util.inputDelete($(this)) ;
			})

		})
	}

	//删除之后,重新刷新
	exports.deleteFaceAfterInSharing = function(data){
		//关闭对话框
		var dialog = util.getCommonDialog() ;
		if (dialog){dialog.close();}
		
		$('.searchWrap a[data-beforeajax="searchFace"]').trigger('click') ;
	}

})