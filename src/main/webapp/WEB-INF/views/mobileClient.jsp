<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ include file="/inc/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv='Content-Type' content='text/html; charset=utf-8' />
<meta chartset="utf-8" />
<meta name="viewport" content="width=device-width,initial-scale=1.0,user-scalable=no"/>
<title>人脸识别客户端</title>
<link rel="stylesheet" type="text/css" href="${ctx}/css/mobile/style.css">
<link rel="stylesheet" type="text/css" href="${ctx}/css/mobile/media.css" >
</head>
<body>
    <%
    String subjectName = (String)request.getAttribute("subjectName") ;
    Long subjectId = (Long)request.getAttribute("subjectId") ;
     %>  
    <header>
        ${subjectName}
    </header>
    <input id="storeCtx" value="${ctx}" style="display:none;" >
    <form action="${ctx}/subject/addParticipants.do" id="mobileForm" method="POST">
        <div class="photo">
            <div class="preview">
                <img class="previewImg" id="previewImg" src="${ctx}/img/mobile/bg_photo.png"></img>
            </div>
            <div class="a-btn">上传头像
                <input type="file" id="uploadPhoto" accept="image/gif,image/jpeg,image/png,image/bmp">
                <input type="text" id="imgPath" name="imgPath">
                <input type="text" id="subjectId" name="subjectId" value="${subjectId}">
                <div class="mobilePreviewSubmitWrap">
                    <form>
                    </form>
                    <form action="${ctx}/mobile/upload.do" id="uploadImgIniframe" class="uploadImgIniframe" enctype="multipart/form-data" method="POST">
                        <input type="file" id="uploadImg" name="filename" style="width:150px;height:35px;position:relative;left:-20px;top:-10px;" accept="image/gif,image/jpeg,image/png,image/bmp">
                    </form>
                </div>
            </div>
            <div class="radio-wapper">
                <div class="inline-li fl">
                    <input type="radio" id="man" checked="checked" name="gender" value="男" style="display:inline-block">
                    <label for="man">男</label>
                </div>
                <div class="inline-li fl">
                    <input type="radio" id="woman" name="gender" value="女" style="display:inline-block">
                    <label for="woman">女</label>
                </div>
                <div class="cb"></div>
            </div>
        </div>
        <form id="extend">
        <div class="input-wapper">
            <div class="input-list">
                <li class="input-icon fl name"></li>
                <span class="input-span fl">姓&nbsp;&nbsp;&nbsp;&nbsp;名</span>
                <input id="name" type="text" class="input-text fl" placeholder="请输入真实姓名" name="name" value=""></input>
            </div>
            <div class="input-line"></div>
            <div class="input-list">
                <li class="input-icon fl work"></li>
                <span class="input-span fl">职&nbsp;&nbsp;&nbsp;&nbsp;位</span>
                <input id="job" type="text" class="input-text fl" name="job" placeholder="请输入职位" value=""></input>
                <!--<div class="upOrdown fl">
                    <li class="icon-up"></li>
                    <li class="icon-down"></li>
                </div>-->
            </div>
            <div class="input-line"></div>
            <div class="input-list">
                <li class="input-icon fl home"></li>
                <span class="input-span fl">单&nbsp;&nbsp;&nbsp;&nbsp;位</span>
                <input id="department" type="text" class="input-text" placeholder="请输入所在单位" name="department" value=""></input>
            </div>
            <div class="input-line"></div>
            <div class="input-list">
                <li class="input-icon fl phone"></li>
                <span class="input-span fl">手&nbsp;&nbsp;&nbsp;&nbsp;机</span>
                <input id="mobile" type="text" class="input-text fl" value="" name="mobile" placeholder="请输入11位手机号"></input>
                <div class="reset fl"></div>
            </div>
        </div>
        </form>
        <button type="submit" class="btn submitAll">立即报名</button>
    </form>
    <footer>
    </footer>
    <div id="mobileTipWrap"></div>
<script src="${ctx}/js/libs/jquery/jquery.min.js"></script>
<script src="${ctx}/js/libs/form/jquery.form.min.js"></script>
<script src="${ctx}/js/mobile/mobile.js"></script>

</body>
</html>