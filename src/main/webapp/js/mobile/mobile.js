//页面dom加载完毕之后
$(function(){
    //定义全局值, 标记是否可以进行表单提交操作
    var isImgExist = false ;

    //操作一
    //内部预览图片提交方式
    $('#uploadImg').on('change',function(){
        //重新选择了图片, 此时不能提交
        isImgExist = false ;

        //js校验文件类型
        function testFile(fileDom){
            var imgType = ['gif','jpeg','jpg','png','bmp'] ;
            var arr = $(fileDom).val().split('.') ;
            var arrLength = arr.length ;
            var t= $.inArray(arr[arrLength-1],imgType);
            return t
        }
        if ( testFile($('#uploadImgIniframe').find('#uploadImg').get(0))===-1 ) {
            showTips('error','请选择图片',$('#mobileTipWrap'),false) ;   
            return false;
        };

        showTips('success','本地预览中...',$('#mobileTipWrap'),true) ; 

        var MAX_WIDTH = $('#previewImg').width() ;
        var MAX_HEIGHT = $('#previewImg').height() ;
        var domObj = $('.preview')[0] ;
        if (document.getElementById('uploadImg').files && document.getElementById('uploadImg').files[0]){
            domObj.innerHTML='<img id="preview">' ;

            var img = $('#preview')[0] ;

            var reader = new FileReader() ;
            reader.onload =function(evt){
                showTips('success','正在上传...',$('#mobileTipWrap'),true) ;   
                img.src = evt.target.result;
                
                $('#uploadImgIniframe').ajaxSubmit(function(data){
                    data = JSON.parse(data) ;
                    if (data.status){
                        //将获得到的服务器端的地址填充给隐藏input
                        $('#imgPath').val(data.data) ;
                        showTips('success','图片上传成功',$('#mobileTipWrap'),false) ; 
                        var prevStr = window.location.host;
                        img.src = 'http://'+prevStr + '/' + data.data ;
                        //上传成功了,此时可以提交
                        isImgExist = true ;  

                    }else{
                        showTips('error','图片不符规格',$('#mobileTipWrap'),false) ;  
                        img.src = "";
                    }
                    //阻止跳转
                    return false ;
                });
                $('#uploadImgIniframe').submit() ;
            }
            var file = document.getElementById('uploadImg').files[0] ;
            reader.readAsDataURL(file) ; 
        }else if(objPreviewFake.filters){
            var objPreview = document.getElementById('preview');     
                document.getElementById('uploadImg').select();
            document.getElementById('uploadImg').blur();
                var imgSrc = document.selection.createRange().text;
            objPreviewFake.filters.item('DXImageTransform.Microsoft.AlphaImageLoader').src = imgSrc;
            objPreview.style.display = 'none';
        }
    });
    
    //重置操作
    $('.reset').on('click',function(){
    	$('#mobile').val("");
    });
    //操作二
    //提交表单
    $('.btn.submitAll').off('click') ;
    $('.btn.submitAll').on('click',function(event){ 
        $('#mobileForm').submit();
    })
    $('#mobileForm').submit(function(){
        //触发总体表单提交事件时, 第一操作是判断图片是否满足上传需求
        if (!isImgExist){
            //当前没有提示语
            if ($('.mobileTips').size() === 0){
                //那就提示一个
                showTips('error','请上传头像',$('#mobileTipWrap'),false) ;
            }else{
                
            }
            return false ; 
        }

        //先进行判断检验操作吧
        if($('input[name="name"]').val()===''){
            showTips('error','姓名不能为空',$('#mobileTipWrap'),false) ;
            return false ;
        }else if($('input[name="imgPath"]').val()===''){
            showTips('error','请上传头像',$('#mobileTipWrap'),false) ;
            return false ;
        }else if($('input[name="job"]').val()===''){
            showTips('error','职位不能为空',$('#mobileTipWrap'),false) ;
            return false ;
        }else if($('input[name="department"]').val()===''){
            showTips('error','单位不能为空',$('#mobileTipWrap'),false) ;
            return false ;
        }else if( $('input[name="mobile"]').val()==='' || !(new RegExp(/^[0-9]{11,11}$/).test($('input[name="mobile"]').val())) ){
            showTips('error','手机号不正确',$('#mobileTipWrap'),false) ;
            return false ;
        }
        
        var checkParams = {
            name: decodeURI($('input[name="name"]').val()) ,
            department: decodeURI($('input[name="department"]').val())
        }
        var winlocation = window.location ;
        var valueStr = window.location.pathname.substr(1).indexOf('/')+2 ;
        // checkParams = JSON.stringify(checkParams) ;
        // checkParams = decodeURI(checkParams) ;
        // 获取当前id
        var subjectIdFromLocation = parseInt( winlocation.href.match(/[0-9]+(?=\/reg.do)/)[0] );

        checkParams = JSON.stringify(checkParams) ;
        showTips('success','信息校验中...',$('#mobileTipWrap'),true) ;
        
        $.ajax({
            url: $('#storeCtx').val()+'/mobile/isUserAvailable.do?name='+$('input[name="name"]').val()+'&department='+$('input[name="department"]').val()+'&subjectId='+subjectIdFromLocation ,
            data: {} ,
            type: 'POST' ,
            dataType: 'json' ,
            // contentType: 'application/json' ,
            processDate: false,
            success:function(dataCheck,status,xhr){
                if (dataCheck.status){
                    if (dataCheck.key==="success.user.isexist"){
                        showTips('error','人员已存在',$('#mobileTipWrap'),false)
                    }else if(dataCheck.key==="success.operation"){
                        // showTips('success','图片校验成功',$('#mobileTipWrap'),true)

                        var paramsData = $('#mobileForm').serializeArray() ;
                        var extendData = $('#extend').serializeArray() ;
                        for (var i=0;i<extendData.length;i++){
                            paramsData.push(extendData[i]) ;
                        }
                        //对象序列化
                        var finalDataAfter = {} ;
                        for (var j=0;j<paramsData.length;j++){
                            var eachObj = paramsData[j] ;       
                            finalDataAfter[eachObj.name] = eachObj.value ;
                        }
                        finalDataAfter.imgPath = [finalDataAfter.imgPath] ;
                        //JSON化
                        finalDataAfter = JSON.stringify(finalDataAfter) ;
                        showTips('success',"正在报名...",$('#mobileTipWrap'),true) ;
                        $.ajax({
                            url: $('#storeCtx').val() + '/mobile/addParticipants.do',
                            data: finalDataAfter ,
                            type: 'POST' ,
                            dataType: 'json' ,
                            contentType: 'application/json' ,
                            processDate: false,
                            success:function(data,status,xhr){
                                if (data.status){
                                    showTips('success','报名成功！',$('#mobileTipWrap'),false) ;
                                    // $('.btn.submitAll').text('报名成功') ;
                                    // $('.btn.submitAll').css({'background-color':'#919191'}) ;
                                    $('input#name').val('') ;
                                    $('input#job').val('') ;
                                    $('input#department').val('') ;
                                    $('input#mobile').val('') ;
                                }else{
                                    showTips('error','信息提交失败!',$('#mobileTipWrap'),false);
                                }
                            }
                        })  
                    }
                }else{
                    showTips('error','人员信息检验失败!',$('#mobileTipWrap'),false);
                }
            }
        })  
     
        return false 
    }) ;

    function showTips(type,message,$wrap,isStay){
        if (type==="success"){
            var eleTips = '<div class="success mobileTips">'+message+'</div>' ;
        }else if(type==="error"){
            var eleTips = '<div class="error mobileTips">'+message+'</div>' ;
        }
        if( !$wrap){
            $wrap = $('#mobileTipWrap') ;
        }

        $wrap.empty().html(eleTips) ;

        var VIEW_WIDTH = $(window).width() ;
        var VIEW_HEIGHT = $(window).height() ;

        var TIP_WIDTH = $wrap.width() ;
        var TIP_HEIGHT = $wrap.height() ;

        $wrap.css({'left': VIEW_WIDTH/2-70,'top': VIEW_HEIGHT/2-TIP_HEIGHT/2}) ;

        if (isStay===false){
            setTimeout(function(){
                $('.mobileTips').remove() ;
            },3000)
        }else{
            // setTimeout(function(){
            //     $('.mobileTips').remove() ;
            // },20000)
        }
    }
    function clacImgZoomParam( maxWidth, maxHeight, width, height ){
        var param = {top:0, left:0, width:width, height:height};
        if( width>maxWidth || height>maxHeight )
        {
            rateWidth = width / maxWidth;
            rateHeight = height / maxHeight;         
            if( rateWidth > rateHeight )
            {
                param.width =  maxWidth;
                param.height = Math.round(height / rateWidth);
            }else
            {
                param.width = Math.round(width / rateHeight);
                param.height = maxHeight;
            }
        }
        param.left = Math.round((maxWidth - param.width) / 2);
        param.top = Math.round((maxHeight - param.height) / 2);
        return param;
    };
    
    

})