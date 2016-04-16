define(function(require,exports,module){
	var $ = require('jquery') ;
	var util = require('utils/index') ;
	var view = require('utils/view') ;

	//ajax后,展示左侧主题列表
	exports.showThemeList = function(data){
		//关闭对话框
		var dialog = util.getCommonDialog() ;
		if (dialog){dialog.close();}

		//获取模板文件
		var tpl = require('text!{tpls}/themeManage/topicNavbar.htm') ;

		view.display($('#bodyer'),tpl,data,function($wrap){
			//为左侧主题绑定点击切换选中效果的事件
			$('.eachTheme .themeName').on('click',function(event){
				//绑定配置按钮一系列下拉选择操作
				_bindNavSmallListEvent(event) ;
			})
			//点击主题库后,触发最开始主题的点击动作
			$('.ThemeList .eachTheme:first-child a').trigger('click') ;
		})
	}

	function _bindNavSmallListEvent(event){
		//点击input元素时不触发切换重置事件
		var $firstTarget = $(event.target) ;
		if ($firstTarget.hasClass('themeNameInput')){
			event.stopPropagation() ;
			return ;
		}
		$('.eachTheme').removeClass('active') ;
		$('.icon.icon-setting').find('.smallNavList').remove() ;
		var $target = $(event.currentTarget).parent('.eachTheme') ; 
		$target.addClass('active') ;
		//获取主题编号
		var href = $('.eachTheme.active').find('a.themeName').attr('href') ;
		var idValue = parseInt( href.substr( href.indexOf('?')+1+3 ) );
		var tpl ='<div class="smallNavList now">'
				+	'<ul class="smallNavUl">'
				+		'<li class="smallNavLi">'
				+			'<a data-beforeAjax="editSubjetTheme">编辑</a>'
				+		'</li>'
				+		'<li class="smallNavLi">'
				+			'<a href="subject/delete.do?id='+idValue+'" data-beforeAjax="deleteSubjectNavBefore" data-afterAjax="deleteSubjectNavAfter" data-confirm="确认删除该主题？">删除</a>'
				+		'</li>'
				+		'<li class="smallNavLi">'
				+			'<a data-beforeAjax="cloneSubjetThemeBefore" data-afterAjax="cloneSubjetThemeAfter" href="subject/copy.do">复制</a>'
				+		'</li>'
				+	'</ul>'
				+'</div>' ;
		var tplTurn ='<div class="smallNavList turn">'
					+	'<ul class="smallNavUl">'
					+		'<li class="smallNavLiTurn">'
					+			'<a href="subject/update.do" data-beforeAjax="editSubjetThemeEnterBefore" data-afterAjax="editSubjetThemeEnterAfter">确定</a>'
					+		'</li>'
					+		'<li class="smallNavLiTurn">'
					+			'<a data-beforeAjax="cancelAddSubject" href="defaultEvent">取消</a>'
					+		'</li>'
					+	'</ul>'
					+'</div>' ;
		$target.find('.icon.icon-setting').append(tpl+tplTurn) ;
		$('.smallNavList.turn').hide() ;

	}

	//ajax后,展示右侧主题区域
	exports.showEachThemeAfter = function(data){
		//关闭对话框
		var dialog = util.getCommonDialog() ;
		if (dialog) {dialog.close() ;}
		
		//获取模板
		var tpl = require('text!{tpls}/themeManage/topicList.htm') ;
		view.display($('#topicList'),tpl,data,function($wrap){
			//下载模版hover提示
			$('.downModelHoverTips').hover(function(event){
				$('.bubbleDialog').show('fast') ;
			},function(event){
				$('.bubbleDialog').hide('fast') ;
			}) ;

			//增加input Enter支持
			$('.searchWrap input').on('keydown',function(event){
				var $target = $(event.currentTarget) ;
				if (event.keyCode === 13){
					$('.searchWrap a').trigger('click') ;
					return ;
				}
				//input绑定删除重置功能.
				util.inputDelete($(this)) ;
			})				

			//获取subjectId
			var idVal = $('.eachTheme.active').find('a.themeName').attr('href') ;
			var subjectIdStore = parseInt( idVal.substr(idVal.indexOf('?')+1+3) ) ;

			//文件上传控制
			//uploader init
			var uploader = WebUploader.create({
			    // swf文件路径
			    swf: 'js/libs/webuploader/Uploader.swf'
			    ,
			    // 文件接收服务端。
			    server: 'subject/batchUpload.do?subjectId='+subjectIdStore
			    ,
			    // 选择文件的按钮。可选。
			    // 内部根据当前运行是创建，可能是input元素，也可能是flash.
			    pick: {
			    	id : '#picker',
			    	innerHTML : 'Lily',	
			    	multiple : false
			    }
			    ,
			    accept: {
			    	extensions :'zip' //允许的文件后缀，不带点，多个用逗号分割。
			    }
			    ,
			    // 不压缩image, 默认如果是jpeg，文件上传前会压缩一把再上传！
			    resize: false
			    ,
			    auto: true //有文件选择完毕即开始上传
			    ,
			    method: 'POST'
			});
			//记录文件状态,默认是正确的
			var errorMsgFlag = "correct" ;
			//error
			uploader.on('error',function(type){
				switch (type){
					case 'Q_TYPE_DENIED' :
						util.showTip('error','文件类型只能为zip！') ;
						break ;
					default:
						util.showTip('error','文件选择异常')
				}
				errorMsgFlag = "wrong" ;
				$('.uploadPro-wrap').remove() ;				
			});
			//文件开始上传时触发,展示进度条
			uploader.on('startUpload',function(){
				if (errorMsgFlag === "correct"){
					$('.uploadPro-wrap').remove() ;
					var tpl = require('text!{tpls}/progress.htm') ;
					$(window.document.body).append(tpl) ;

				}else{
					errorMsgFlag === "correct" ;
				}
			});
			uploader.on( 'uploadProgress', function( file, percentage ) {
				// percentage = Math.floor(percentage) ;
				if (percentage === 0){

				}else{
			    	$('.percent').text((Math.floor(percentage*100)-1)+'%') ;
			    	$('.progressInside').css({'width':(Math.floor(percentage*100)-1)+'%'}) ;
			    }
			});
			//resourse是服务端返回的数据
			uploader.on( 'uploadSuccess', function( file , response ) {
				//移除已上传文件
				uploader.removeFile( file );
			    
			   	$('.uploadName').text('上传结果：') ;

			   	if (response.status === true) {
			   		util.showTip('success','文件上传成功，无错误信息' ) ;
			   		$('.uploadPro-wrap').remove() ;		
			   	}else if(response.status === false){
			   		if ( response.data !== null ){
					   	//获取失败人数
					   	var failVal = response.data.length ;
					   	
					   	$('.progress').css({'background-color':'#ffffff'}).html('其中<span style="color:red">'+failVal+'</span>人因照片信息错误导入失败') ;

					   	$('.percent').html('<a class="failShow" data-beforeajax="showFailUploadFaceDialog" href='+response.key+' style="color:#4094fc">查看</a><span class="closeFailResult">X</span>') ;
					   	$('.failShow').data('failfacestore',JSON.stringify(response));
					   	//绑定叉号关闭事件
					   	$('.closeFailResult').off('click') ;
					   	$('.closeFailResult').on('click',function(){
					   		$('.uploadPro-wrap').remove() ;
					   	}) ;
					}else if(response.data === null){
						util.showTip('error','文件解析出错，请重新上传' ) ;
			   			$('.uploadPro-wrap').remove() ;	
					}
			   	}
			   	//触发刷新
			   	$('.eachTheme.active a.themeName').trigger('click') ;	   	

			});
			//上传出错,reason是错误code
			uploader.on( 'uploadError', function( file , reason ) {
			    util.showTip('error','上传出错，错误码:'+reason ) ;
			});
			//不管成功与否,上传完了就触发
			// uploader.on( 'uploadComplete', function( file ) {
			//     $( '#'+file.id ).find('.progress').fadeOut(); 
			// });
		})
		
	}


	//ajax后,刷新左侧主题区域
	exports.showNewNavbarList = function(data,$target){
		if (data.key === 'success.subject.isexist'){
			//主题已存在,虽然status是true,但还是不让添加
			// util.showTip('error','主题已存在，请更改名称') ;
			util.showNavTips('error','主题已存在，请更改名称',$('.addNewTheme')[0]) ;
			return ;		
		}
		var params = {
			subjectId : data.data
		}
		var dataStore = data ;
		$.ajax({
 			url:'subject/generateQRCode.do', //目标地址
 			data:params, //post参数
 			type:'POST', //POST
 			dataType:'json', //期望返回json
 			processDate: false,
 			success:function(data,status,xhr){
 				if (data.status){
					var imgSrc = {
						imgSrc : data.data	
					} ;
					//关闭对话框
					var dialog = util.getCommonDialog() ;
					if (dialog){dialog.close();}

					//状态重置
					$('.addThemeWrap').hide() ;
					$('input.addThemeInput').val('') ;
					$target.attr('isShow',false) ;
					$('a.changeCancelA').text('取消').data('beforeajax','cancelAddTheme').attr('href','') ;
					
					//获取模板文件
					var tpl = require('text!{tpls}/themeManage/topicNavbarEach.htm') ;

					//创建一个<div class="eachTheme">,待会把模板渲染进去
					var eachTheme = '<div class="eachTheme"></div>' ; 
					$('.ThemeList').prepend(eachTheme) ;

					view.display($('.eachTheme:first-child'),tpl,dataStore,function($wrap){
						//为左侧主题绑定点击切换选中效果的事件
						$('.eachTheme .themeName').off('click') ;
						$('.eachTheme .themeName').on('click',function(event){
							//绑定配置按钮一系列下拉选择操作
							_bindNavSmallListEvent(event) ;
						})
						//提示成功
						util.showNavTips('success','新建主题成功',$('.addNewTheme')[0]) ;
						//点击主题库后,触发最开始主题的点击动作
						
						//判断此时左侧列表是否只有一个主题,只有一个的时候就自动触发该元素的click事件
						if ($('.eachTheme').length === 1){
							$('.eachTheme .themeName').trigger('click') ;
						}

					})
				}else{
					util.showTip('error','二维码获取失败!') ;
				}
 			},
 			error:function(xhr,status,error){
 					util.showTip('error','请求二维码失败!') ;
	 			}		
 		}) ;


		
	}
	
	//新增参会人员之后
	exports.addSubjectAfter = function(data){
		//操作成功!
		if (data.key === 'success.operation') {
			util.getCommonDialog().close() ;
			//当前激活的就是当前添加进去的那个主题,此主题触发点击事件重新刷新
			$('.eachTheme.active a').trigger('click') ;
			
			util.showTip('success','添加人员信息成功!') ;
		}else{
			util.showTip('error','添加人员信息失败!') ;
		};
	}

	//删除主题之后,重新点击主题管理重新刷新
	exports.deleteSubjectNavAfter = function(data){
		if (data.status === true){
			util.showTip('success',data.message) ;
		}else{
			util.showTip('error',data.message) ;
		}

		$('#themeManage a').trigger('click') ;
	}

	//更新主题之后, 当前正在编辑的那个主题管理重新触发click 
	exports.editSubjetThemeEnterAfter = function(data){
		if (data.status){
			if ( data.key === 'success.subject.isexist' && !($('input.themeNameInput').val() === $('input.themeNameInput').next().text()) ){
				util.showNavTips('error','主题已存在，请更改名称',$('.addNewTheme')[0]) ;
				return ;
			}
			util.showNavTips('success','修改主题成功',$('.addNewTheme')[0]) ;
			
			var $a = $('.eachTheme.active').find('.themeName') ;
			$input = $('input.themeNameInput') ;
			
			var $nowSmallNav = $('.smallNavList.now') ;
			var $turnSmallNav = $('.smallNavList.turn') ;

			$a.show() ;
			$a.text($input.val()) ;
			$input.remove() ;

			$nowSmallNav.show() ;
			$turnSmallNav.hide() ;

		}else{
			util.showNavTips('error','修改主题失败',$('.addNewTheme')[0]) ;
			return ;
		}

	}

	//搜索结束, 页面刷新
	exports.searchSubjectAfter = function(data){
		//获取模板
		var tpl = require('text!{tpls}/themeManage/topicList.htm') ;

		view.display($('#topicList'),tpl,data,function($wrap){

			// util.inputDelete($(this)) ;
			//增加input Enter支持
			$('.searchWrap input').on('keydown',function(event){
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

	//删除之后,页面刷新
	exports.deleteFaceAfterInThemeMain = function(data){
		//关闭对话框
		var dialog = util.getCommonDialog() ;
		if (dialog){dialog.close();}

		$('.searchWrap a[data-beforeajax="searchInputSubjectBefore"]').trigger('click') ;
	}

	//从公共库导入,点击之后的弹窗处理
	exports.addNewParticipantsAfter = function(data){
		//关闭对话框
		var dialog = util.getCommonDialog() ;
		if (dialog){dialog.close();}
		//预处理数据,添加当前主题名称和ID
		var subjectNameStore = $('.eachTheme.active').find('a.themeName').text();
		var idVal = $('.eachTheme.active').find('a.themeName').attr('href') ;
		var subjectIdStore = parseInt( idVal.substr(idVal.indexOf('?')+1+3) ) ;

		data = $.extend(data,{subjectNameStore : subjectNameStore},{subjectIdStore : subjectIdStore}) ;

		var tpl = require('text!{tpls}/faceLibrary/bodyerFromTheme.htm') ;

		view.display('dialog',tpl,data,function(dialog,$wrap){

			dialog.width(850).showModal();

			//叉号绑定搜索框
			$('.icon.icon-closeDialog').on('click',function(){
				var dialog = util.getCommonDialog() ;
				if (dialog) {dialog.close();} 
			})
			//增加input Enter支持
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

	//加入公共库
	exports.addToShareAfter = function(data){
		if (data.status){
			util.showTip('success','成功添加至公共库!') ;
		}else{
			util.showTip('error',data.message) ;
		}

	}

	//从公共库导入到主题, 确认
	exports.addFromSharingAfter = function(data){
		if (data.status){
			util.showTip('success','成功导入至当前主题!') ;
			var dialog = util.getCommonDialog() ;
			if (dialog) {dialog.close() ;} 

			$('.eachTheme.active').find('a.themeName').trigger('click') ;

		}else{
			util.showTip('error',data.message) ;

		}

	}

	//从公共库导入到主题, 搜索按钮
	exports.searchFaceFormSharingAfter = function(data){
		//关闭对话框
		var dialog = util.getCommonDialog() ;
		if (dialog){dialog.close();}
		//预处理数据,添加当前主题名称和ID
		var subjectNameStore = $('.eachTheme.active').find('a.themeName').text();
		var idVal = $('.eachTheme.active').find('a.themeName').attr('href') ;
		var subjectIdStore = parseInt( idVal.substr(idVal.indexOf('?')+1+3) ) ;

		data = $.extend(data,{subjectNameStore : subjectNameStore},{subjectIdStore : subjectIdStore}) ;

		var tpl = require('text!{tpls}/faceLibrary/bodyerFromTheme.htm') ;

		view.display('dialog',tpl,data,function(dialog,$wrap){

			dialog.width(1000).showModal();

			//叉号绑定搜索框
			$('.icon.icon-closeDialog').on('click',function(){
				var dialog = util.getCommonDialog() ;
				if (dialog) {dialog.close();} 
			})
			$('.faceLibraryInput').val(data.params.name);
			//增加input Enter支持
			$('.faceLibraryInput').off('keydown') ;
			$('.faceLibraryInput').on('keydown',function(event){
				var $target = $(event.currentTarget) ;
				if (event.keyCode === 13){
					$('div[i="dialog"]').find('.searchWrap a').trigger('click') ;
					return ;
				}
				//input绑定删除重置功能.
				util.inputDelete($(this)) ;
			})

		})
	}


	//克隆主题之后, 点击生成的主题, 刷新页面
	exports.cloneSubjetThemeAfter = function(data,$target){
		if (data.status){
			if (data.key === 'success.subject.isexist'){
				util.showNavTips('error','该主题副本已存在',$('.addNewTheme')[0]) ;
			}else if(data.key === 'success.operation'){
				//复制主题成功了, 还有二维码撒
				util.showNavTips('success','复制主题成功',$('.addNewTheme')[0]) ;
				//请求二维码!
				var params = {
					subjectId : parseInt(data.data)
				}
				$.ajax({
		 			url:'subject/generateQRCode.do', //目标地址
		 			data:params, //post参数
		 			type:'POST', //POST
		 			dataType:'json', //期望返回json
		 			processDate: false,
		 			success:function(data,status,xhr){
		 				if (data.status){
							//关闭对话框
							var dialog = util.getCommonDialog() ;
							if (dialog){dialog.close();}
							$('#themeManage a').trigger('click') ;
						}else{
							util.showTip('error','二维码获取失败!') ;
						}
		 			},
		 			error:function(xhr,status,error){
		 					util.showTip('error','请求二维码失败!') ;
			 			}		
		 		}) ;
			}
		}else{
			util.showNavTips('error','复制主题失败',$('.addNewTheme')[0]) ;
			return ;
		}
	}
})