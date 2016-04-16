define(function(require,exports,module){
	var $ = require('jquery') ;
	var util = require('utils/index') ;
	var view = require('utils/view') ;

	//TEST
	var formPlugin = require('jquery.form') ;

	//ajax前,对url进行处理,返回url和解析后的params
	exports.showEachThemeBefore = function(url,$target){

		if (url.indexOf('?')>= 0){
			var urlCut = url.substr(0,url.indexOf('?')) ;
			var parmsArr = url.substr(url.indexOf('?')+1).split('&') ;
			var params = {} ;
			for (var i=0;i<parmsArr.length;i++){
				var everyParamArr = parmsArr[i].split('=') ;
				if (everyParamArr[0] === 'id'){
					params[everyParamArr[0]] = parseInt(everyParamArr[1]) ;
				}else{
					params[everyParamArr[0]] = everyParamArr[1] ;
				}
			}

		}else{
			var urlCut = url ;
			params = {} ;
		}
		return {
			url : urlCut ,
			params : params
		}

	}

	//点击新建主图,出来个新建的框
	exports.showAddTheme = function(url,$target){
		if (!$target.attr('isShow') || $target.attr('isShow')==='false') {
			$target.attr('isShow',true) ;
			$('.addThemeWrap').show() ;
		}else{
			$('.addThemeWrap').hide('fast') ;
			$target.attr('isShow',false) ;
		}

		// input框键入事件绑定,以keyup为判断指标
		$('input.addThemeInput').off('keyup') ;
		$('input.addThemeInput').on('keyup',function(event){
			var event = $.event.fix(event || window.event);
	 		var $target = $(event.currentTarget) ;
	 		if ($target.val() === ''){
	 			$('a.changeCancelA').text('取消').data('beforeajax','cancelAddTheme').attr('href','') ;
	 		}else{
	 			if($target.val().length > 25){
	 				$target.val($target.val().substring(0,26));
	 				util.showNavTips('error','主题名不能超过25个字符',$('.addNewTheme')[0]) ;
	 			}
	 			$('a.changeCancelA').text('确定').data('beforeajax','operationThemeWrap').attr('href','subject/save.do') ;
	 		}
	 		if (event.keyCode === 13){
				$('.addThemeWrap a').trigger('click') ;
			}
		}) ;
		//输入框聚焦
		$('input.addThemeInput').focus() ;

		return {
			isStop : true 
		}
	}

	//取消新建框
	exports.cancelAddTheme = function(url,$target){
		$('.addThemeWrap').hide() ;
		return {
			isStop : true
		}
	}

	//新建操作,从input框中取数据
	exports.operationThemeWrap = function(url,$target){
		if ($target.prev().val() === ''){
			util.showNavTips('error','主题名不能为空',$('.addNewTheme')[0]) ;
			return {
				isStop : true
			}
		}else if($target.prev().val().length > 25 ){
			util.showNavTips('error','主题名不能超过25个字符',$('.addNewTheme')[0]) ;
			return {
				isStop : true
			}
		}

		return {
			url : url,
			params : {
				subjectName : $target.prev().val()
			}
		}
	}


	//从公共库添加
	exports.addNewParticipants = function(url,$targte){
	}

	//微弹窗第一个处理链接,新建个人
	exports.addSingleParticipants = function(){
		//关闭对话框
		var dialog = util.getCommonDialog() ;
		if (dialog) {dialog.close() ;}

		//获取模板
		var tpl = require('text!{tpls}/themeManage/addNewParticipants.htm') ;

		view.display('dialog',tpl,{},function(dialog){
			if (dialog.open) {
                return;
            }
            dialog.width(600).showModal();
            $('.ui-dialog-header').show();

            //其下的form表单触发init初始化事件
            $(dialog).find('form').trigger('init') ;

            //获取当前激活的主题的id
            var idVal = $('.eachTheme.active a').attr('href') ;
			var regValue = parseInt( idVal.substr(idVal.indexOf('?')+1+3) ) ;
			//赋值,新建个人里的
			$('#subjectId').val(regValue) ;

			//多图片切换的事件绑定
			$('.multPhoto').off('click','.eachPhoto_cursor') ;
			$('.multPhoto').on('click','.eachPhoto_cursor',function(event){
				var $target = $(event.target) ;
				$('.eachPhoto_cursor').removeClass('active') ;
				$target.addClass('active') ;
				$('.imgPreviewSubmit').css({'z-index':'1'}) ;
				$('.uploadImgBtn').text('上传照片').css({'background-color':'#499aff'}) ;
				//iframe z轴控制,使其不可点击
				if ( $('.eachPhoto_cursor').length>=10 && ($target.text())==='+' ){	
					$('.imgPreviewSubmit').css({'z-index':'-10'}) ;
					$('.uploadImgBtn').text('照片数量已达上限').css({'background-color':'rgb(175, 175, 175)'}) ;
					return ;
				} ;

				//获取id数字
				var nowImgIndex = $target.get(0).id.substr(9) ;
				if (nowImgIndex!==''){	
					//切换预览图
					if ($('#preview').get(0)){
						//获取相对路径
						var localtionVal = 'http://'+window.location.host+'/' ;
						$('#preview').get(0).src = localtionVal + $('input#imgPath'+nowImgIndex).val()	;		
					}else{
						var domObj = $('.uploadInstead').get(0) ;
						domObj.innerHTML = '<img id="preview">' ;
						//获取相对路径
						var localtionVal = 'http://'+window.location.host+'/' ;
						$('#preview').get(0).src = localtionVal + $('input#imgPath'+nowImgIndex).val()	;				 
					}
					//切换上传按钮的text提示
					$('.uploadImgBtn').text('更换照片') ;
					$('.uploadImgBtn').css({'background-color':'rgb(255, 159, 73)'}) ;
				}else{
					var imgDefault = '暂无照片<img alt="暂无照片" class="pa" border=0 src="" style="display:none">' ;
					$('.uploadInstead').html(imgDefault) ;
					$('.uploadImgBtn').text('上传照片') ;
					$('.uploadImgBtn').css({'background-color':'#499aff'}) ;
				}		
			}) ;
			//active图片hover删除图标出现
			$('.multPhoto').off('click','.deleteImg.hover') ;
			$('.multPhoto').on('click','.deleteImg.hover',function(event){
				//删除该img与其对应的input
				var $target = $(event.target) ;
				var $imgParent = $target.parent() ;
				$target.remove() ;
				var imgIndex = $imgParent.text() ;
				$imgParent.remove() ;
				$('#imgPath'+imgIndex).remove() ;

				//遍历剩下除了最后+号的元素,重排其顺序,赋予新的index
				var $imgRemain = $('.eachPhoto_cursor') ;
				for(var i=1;i<$imgRemain.length;i++){
					//替换其id
					$imgRemain.eq(i-1).get(0).id = 'imgCursor'+i ;
					//替换其内容
					var replaceStr = i+'<div class="deleteImg hover">X</div>' ;
					$imgRemain.eq(i-1).html(replaceStr) ;
				}
				//遍历input,赋予新的id
				var $imgInput = $('input[name="imgPath"]') ;
				for (var i=1;i<$imgInput.length+1;i++){
					//替换其id
					$imgInput.get(0).id = 'imgPath'+i ; 
				}

				//触发第一个按钮的点击事件,切换默认选择
				$('.eachPhoto_cursor').eq(0).trigger('click') ;

			}) ;

		});

		//通知处理器,不需要发请求了直接返回
		return {
			isStop : true 
		};

	}

	//新增参会人员信息之前的处理
	exports.addSubjectBefore = function(){
		if (!$('input[name="imgPath"]').val()){
			util.showTip('error','您还没有上传图片',$('.dialog-tips')) ;
			return {
				isStop : true
			};
		}
	}


	
	//编辑参会人员前
	exports.editSubjectFace = function(url,$target){
		//关闭对话框
		var dialog = util.getCommonDialog() ;
		if (dialog) {dialog.close() ;}

		//获取模板
		var tpl = require('text!{tpls}/themeManage/editParticipants.htm') ;
		//url版json串变成object啦
		url = JSON.parse(url) ;

		view.display('dialog',tpl,url,function(dialog){
			if (dialog.open) {
                return;
            }
            dialog.width(600).showModal();
            $('.ui-dialog-header').show();

            //其下的form表单触发init初始化事件
            $(dialog).find('form').trigger('init') ;

            //获取当前激活的主题的id
            var idVal = $('.eachTheme.active a').attr('href') ;
			var regValue = parseInt( idVal.substr(idVal.indexOf('?')+1+3) );
			//赋值,编辑个人里的
			$('#subjectId').val(regValue) ;

			//多图片切换的事件绑定
			$('.multPhoto').off('click','.eachPhoto_cursor') ;
			$('.multPhoto').on('click','.eachPhoto_cursor',function(event){
				var $target = $(event.target) ;
				$('.eachPhoto_cursor').removeClass('active') ;
				$target.addClass('active') ;
				$('.imgPreviewSubmit').css({'z-index':'1'}) ;
				$('.uploadImgBtn').text('上传照片').css({'background-color':'#499aff'}) ;
				//iframe z轴控制,使其不可点击
				if ( $('.eachPhoto_cursor').length>=10 && ($target.text())==='+' ){	
					$('.imgPreviewSubmit').css({'z-index':'-10'}) ;
					$('.uploadImgBtn').text('照片数量已达上限').css({'background-color':'rgb(175, 175, 175)'}) ;
					return ;
				} ;

				//获取id数字
				var nowImgIndex = $target.get(0).id.substr(9) ;
				if (nowImgIndex!==''){	
					//切换预览图
					if ($('#preview').get(0)){
						//获取相对路径
						var localtionVal = 'http://'+window.location.host+'/' ;
						$('#preview').get(0).src = localtionVal + $('input#imgPath'+nowImgIndex).val()	;		
					}else{
						var domObj = $('.uploadInstead').get(0) ;
						domObj.innerHTML = '<img id="preview">' ;
						//获取相对路径
						var localtionVal = 'http://'+window.location.host+'/' ;
						$('#preview').get(0).src = localtionVal + $('input#imgPath'+nowImgIndex).val()	;			 
					}
					//切换上传按钮的text提示
					$('.uploadImgBtn').text('更换照片') ;
					$('.uploadImgBtn').css({'background-color':'rgb(255, 159, 73)'}) ;
				}else{
					var imgDefault = '暂无照片<img alt="暂无照片" class="pa" border=0 src="" style="display:none">' ;
					$('.uploadInstead').html(imgDefault) ;
					$('.uploadImgBtn').text('上传照片') ;
					$('.uploadImgBtn').css({'background-color':'#499aff'}) ;
				}		
			}) ;
			//active图片hover删除图标出现
			$('.multPhoto').off('click','.deleteImg.hover') ;
			$('.multPhoto').on('click','.deleteImg.hover',function(event){
				//删除该img与其对应的input
				var $target = $(event.target) ;
				var $imgParent = $target.parent() ;
				$target.remove() ;
				var imgIndex = $imgParent.text() ;
				$imgParent.remove() ;
				$('#imgPath'+imgIndex).remove() ;

				//遍历剩下除了最后+号的元素,重排其顺序,赋予新的index
				var $imgRemain = $('.eachPhoto_cursor') ;
				for(var i=1;i<$imgRemain.length;i++){
					//替换其id
					$imgRemain.eq(i-1).get(0).id = 'imgCursor'+i ;
					//替换其内容
					var replaceStr = i+'<div class="deleteImg hover">X</div>' ;
					$imgRemain.eq(i-1).html(replaceStr) ;
				}
				//遍历input,赋予新的id
				var $imgInput = $('input[name="imgPath"]') ;
				for (var i=1;i<$imgInput.length+1;i++){
					//替换其id
					$imgInput.get(0).id = 'imgPath'+i ; 
				}

				//触发第一个按钮的点击事件,切换默认选择
				$('.eachPhoto_cursor').eq(0).trigger('click') ;

			}) ;

			//进修改页,默认显示第一张照片,触发第一张图片的click事件
			$('.eachPhoto_cursor').eq(0).trigger('click') ;

		});

		//通知处理器,不需要发请求了直接返回
		return {
			isStop : true 
		};
	}


	//编辑主题,点击之后当前选中的栏变成input框,为后续操作
	exports.editSubjetTheme = function(url,$target){
		var $a = $('.eachTheme.active').find('.themeName') ;
		var inputTpl = '<input class="themeNameInput" value="'+$a.text()+'">' ;
		$a.before(inputTpl).hide() ;

		var $nowSmallNav = $('.smallNavList.now') ;
		var $turnSmallNav = $('.smallNavList.turn') ;

		//$nowSmallNav.after(tplTurn) ;
		
		$nowSmallNav.hide() ;
		$turnSmallNav.show() ;

		var $input = $('input.themeNameInput') ;
		$input.focus() ;

		// 绑定enter支持
		$input.on('keydown',function(event){
			var $target = $(event.currentTarget) ;
			if (event.keyCode === 13){
				$('.smallNavList.turn').find('a[data-beforeajax="editSubjetThemeEnterBefore"]').trigger('click') ;
			}
		})


		$('a[data-beforeAjax="cancelAddSubject"]').on('click',function(event){
			$a.show() ;
			$input.remove() ;

			$nowSmallNav.show() ;
			$turnSmallNav.hide() ;
		});

		//绑定windows触发的事件,点击的不为该input或.icon.icon-setting
		$(window.document.body).off('click') ;
		$(window.document.body).on('click',function(event){
			var event = $.event.fix(event || window.event);
	 		var $target = $(event.target) ;

	 		if ($target.hasClass('themeNameInput') || $target.hasClass('icon-setting') || $target.attr('data-afterajax')==='editSubjetThemeEnterAfter' ){
	 		}else{
	 			$a.show() ;
				$input.remove() ;

				$nowSmallNav.show() ;
				$turnSmallNav.hide() ;
	 		}

		}) ;



		return {
			isStop : true
		}
	}

	// 删除主题
	exports.deleteSubjectNavBefore = function(url,$target){

	}

	//主题编辑确定
	exports.editSubjetThemeEnterBefore = function(url,$target){
		//获取id与subjet名字
		var idVal = $('.eachTheme.active').find('a.themeName').attr('href') ;
		var regValue = parseInt( idVal.substr(idVal.indexOf('?')+1+3) ) ;

		if ($('input.themeNameInput').val() === ''){
			util.showNavTips('error','主题名不能为空',$('.addNewTheme')[0]) ;
			return {
				isStop : true
			}
		}else if($('input.themeNameInput').val().length > 25 ){
			util.showNavTips('error','主题名不能超过25个字符',$('.addNewTheme')[0]) ;
			return {
				isStop : true
			}
		}

		return {
			url : url ,
			params : {
				id : regValue ,
				subjectName : $('.eachTheme.active').find('input').val() 
			}
		}
	}

	//分页之前的操作
	exports.searchSubjectBefore = function(url,$target){
		//获取id与subjet名字

		if (url.indexOf('?')>= 0){
			var urlCut = url.substr(0,url.indexOf('?')) ;
			var parmsArr = url.substr(url.indexOf('?')+1).split('&') ;
			var params = {} ;
			for (var i=0;i<parmsArr.length;i++){
				var everyParamArr = parmsArr[i].split('=') ;
				if (everyParamArr[0] === 'id'){
					params[everyParamArr[0]] = parseInt(everyParamArr[1]) ;
				}else{
					params[everyParamArr[0]] = everyParamArr[1] ;
				}
			}

		}else{
			var urlCut = url ;
			params = {} ;
		}
		return {
			url : urlCut ,
			params : params
		}
	}

	//搜索人脸
	exports.searchInputSubjectBefore = function(url,$target){
		//获取id与subjet名字
		var idVal = $('.eachTheme.active').find('a.themeName').attr('href') ;
		var regValue = parseInt( idVal.substr(idVal.indexOf('?')+1+3) ) ;

		return {
			url : url ,
			params : {
				id : regValue ,
				faceName : $('.searchWrap input').val()
			}
		}
	}

	//加入公共库before
	exports.addToShareBefore = function(url,$target){
		var arr = [] ;
		//获取选中的checkbox
		$('.list .table tbody tr td').find('input').each(function(i){
			if (this.checked){
				arr.push(parseInt($(this).val())) ;
			}
		});
		if (arr.length===0){
			util.showTip('error','请选择至少一条数据') ;
			return {
				isStop : true 
			};
		}
		return {
			url : url ,
			params : {
				ids : arr
			}
		}

	}

	//从公共库导入到主题
	exports.addFromSharingBefore = function(url,$target){
		var arr = [] ;
		$('.table.formSharingToTheme').find('tbody tr td').find('input').each(function(i){
			if (this.checked){
				arr.push(parseInt($(this).val())) ;
			}
		});
		if (arr.length === 0){
			util.showTip('error','请选择至少一条数据',$('.dialog-tips')) ;
			return {
				isStop : true 
			};
		}
		return {
			url : url ,
			params : {
				id : $('#sharingToThemeId').val() ,
				ids : arr
			}
		}
	}

	//展示二维码大图!
	exports.showBigCodeMap = function(url,$target){
		var data = {
			imgSrc : $target.find('img').attr('src') 
		}

		var tpl = require('text!{tpls}/themeManage/bigCodeMap.htm') ;
		
		view.display('dialog',tpl,data,function(dialog,$wrap){
			//展示
			dialog.showModal().width(400) ;
			//叉号绑定搜索框
			$('.icon.icon-closeDialog').on('click',function(){
				var dialog = util.getCommonDialog() ;
				if (dialog) {dialog.close();} 
			})
		}) ;

		return {
			isStop : true
		}
	}

	//test
	//从本地批量添加
	exports.addFromLocal = function($target){
		//关闭对话框
		var dialog = util.getCommonDialog() ;
		if (dialog) {dialog.close() ;}

		$('#picker input[name="file"]').trigger('click') ;

		return {
			isStop : true 
		}
	}


	//展示错误列表
	exports.showFailUploadFaceDialog = function(url,$target){
		//url解析为data,获取key
		var data = JSON.parse( $target.data('failfacestore') ) 

		//获取模板
		var tpl = require('text!{tpls}/themeManage/failFaceList.htm') 

		view.display('dialog',tpl,data,function(dialog,$wrap){
			dialog.showModal().width(800) ;
			//叉号关闭
			$('.icon.icon-closeDialog').on('click',function(){
				var dialog = util.getCommonDialog() ;
				if (dialog) {dialog.close();} 
			})

			//再把错误信息存储在下载按钮上
			//$('.downFailListNative').data('facelisterrorstore',JSON.stringify(data.data)) ;


		}) ;

		//阻止默认跳转
		return {
			isStop : true
		}
	}

	//克隆主题
	exports.cloneSubjetThemeBefore = function(url,$target){
		//获取id与subjet名字
		var idVal = $('.eachTheme.active').find('a.themeName').attr('href') ;
		var regValue = parseInt( idVal.substr(idVal.indexOf('?')+1+3) ) ;
		//获取主题名
		var subjectName = $('.eachTheme.active').find('.themeName').text() ;
		var preview = subjectName+'-副本' ;
		if (preview.length > 25 ){
			util.showNavTips('error','主题名复制后超过25个字符',$('.addNewTheme')[0]) ;
			return {
				isStop : true
			}
		}
		return {
			url : url ,
			params : {
				id : regValue ,
				subjectName : subjectName+'-副本'   
			}
		}
	}

})