define(function(require,exports,module){
	var $ = require('jquery') ;
	var util = require('utils/index') ;
	var view = require('utils/view') ;

	//ajax前,对url进行处理,返回url和解析后的params
	exports.addNewFace = function(url,$target){
		//关闭对话框
		var dialog = util.getCommonDialog() ;
		if (dialog) {dialog.close() ;}

		//获取模板
		var tpl = require('text!{tpls}/faceLibrary/addNewFace.htm') ;

		view.display('dialog',tpl,{},function(dialog){
			if (dialog.open) {
                return;
            }
            dialog.width(600).showModal();
            $('.ui-dialog-header').show();

            //其下的form表单触发init初始化事件
            $(dialog).find('form').trigger('init') ;

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
	};

	//搜索主要的人脸之前
	exports.showFaceListBefore = function(url,$target){
		//关闭对话框
		var dialog = util.getCommonDialog() ;
		if (dialog) {dialog.close() ;}

		if (url.indexOf('?')>= 0){
			var urlCut = url.substr(0,url.indexOf('?')) ;
			var parmsArr = url.substr(url.indexOf('?')+1).split('&') ;
			var params = {} ;
			for (var i=0;i<parmsArr.length;i++){
				var everyParamArr = parmsArr[i].split('=') ;
				if (everyParamArr[0] === 'curPage') {
					params[everyParamArr[0]] = parseInt(everyParamArr[1]) ;
				}else{
					params[everyParamArr[0]] = everyParamArr[1] ;
				};
				
			}

		}else{
			var urlCut = url ;
			params = {} ;
		}
		return {
			url : urlCut ,
			// params : JSON.stringify(params) ,
			params : params
		}

	}

	//ajax请求前
	exports.editFace = function(url,$target){
		//关闭对话框
		var dialog = util.getCommonDialog() ;
		if (dialog) {dialog.close() ;}

		//获取模板
		var tpl = require('text!{tpls}/faceLibrary/editFace.htm') ;
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


	//删除人脸之前,对当前选中的参数的id们进行序列化
	exports.deleteFace = function(url,$target){
		var idArray = [] ;
		$('.list .table tbody tr').find('input[type="checkbox"].deleteChecked').each(function(index,objCheck){
			if ($(objCheck)[0].checked){
				//获取存储的id值
				var nowId = parseInt($(objCheck).val()) ;
				idArray.push(nowId) ;
			} ;
		});
		if (idArray.length===0){
			util.showTip('error','请选择至少一条数据') ;
			return {
				isStop : true 
			};
		}
		return {
			url : url ,
			params : {
				ids : idArray
			} 
		}
	}


	//ajax前,搜索人脸
	exports.searchFace = function(url,$target){
		var shareWord = $target.parent().find('input').val() ;
		if (shareWord == ''){
			return ;
		}
		return {
			url : url ,
			params : {
				name : shareWord
			}
			
		}

	}

	//ajax前,判断图片是否有值
	exports.addFaceBefore = function(){
		if (!$('input[name="imgPath"]').val()){
			util.showTip('error','您还没有上传图片',$('.dialog-tips')) ;
			return {
				isStop : true
			};
		}

	}

	//预览图片
	exports.letsPreviewImg = function(url,$target){
		var pathDate = {
			faceList : null
		};
		// 获取当前激活的值,更改数组值
		var $nowImg = $('.eachPhoto_cursor.active') ;
		if ($nowImg.hasClass('addImg')){

		}else{
			var imgIndex = $nowImg.get(0).id.substr(9) ;
			pathDate.faceList = $('#imgPath'+imgIndex).val() ;
		}
		//根据该数组值进行预览判断
		if (!pathDate.faceList || ($.type(pathDate.faceList) === "array" && pathDate.faceList.length === 0) ){
			util.showTip('error','没有图片无法预览',$('.dialog-tips'));
			return {
				isStop : true 
			}
		}else if($.type(pathDate.faceList) === "string" || ($.type(pathDate.faceList) === "array" && pathDate.faceList.length === 1)){
			var tpl = require('text!{tpls}/faceLibrary/previewFace.htm') ;
			if ($.type(pathDate.faceList) === "string"){
				pathDate.faceList = [pathDate.faceList];
			}
		}else{
			var tpl = require('text!{tpls}/faceLibrary/previewFaceNine.htm') ;
		}
	
		require.async(['juicer'],function(juicer){
			var $wrap = $('<div id="imgShow" style="position: absolute;width: 400px;height: 500px;left: -141px;top: 20px;background-color:#fff;border: 1px solid #dadada;"></div>') ;
			$('.uploadInstead').append($wrap) ;
			$wrap.get(0).innerHTML = juicer(tpl,pathDate) ;
			//叉号关闭
			$('.insideWrap .inside-title .icon.close').on('click',function(){
				$('#imgShow').remove();
			});
			//名称修改
			$('.insideName').text($('.previewImg').data('name')) ;
		})

		return {
			isStop : true
		}

	}


})