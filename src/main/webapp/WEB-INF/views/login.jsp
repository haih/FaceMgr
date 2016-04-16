<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>人脸识别管理系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" type="text/css" href="./css/page/ui-login.css">
<link rel="icon" href="img/ico/lily48.ico" mce_href="img/ico/lily48.ico"
	type="image/x-icon">
<link rel="shortcut icon" href="img/ico/lily48.ico"
	mce_href="img/ico/lily48.ico" type="image/x-icon">
</head>
<body>
	<div class="loginWrap">
		<div class="loginTop"></div>
		<form class="loginForm" action="login.do" method="POST">
		<div class="loginformWrap" style="position: relative; left: -20px">
		  <div id="msg" style="position: absolute;left: 0;margin-top: -30px;right: 0;text-align: center" 
				<c:if test="${msg!=null}"></c:if> ${msg}</div>			
            账号名： 
            <input placeholder="请输入账号名" type="text" name="username">
        </div>
        <div class="loginformWrap" style="position: relative;left: -13px;">
        
            密码：
            <input placeholder="请输入6-8位数密码" type="password" name="password">
        </div>
        <div class="loginformWrap submitBtn" style="position:relative;left: 10px;top:25px">
            <button type="submit">确定</button>
        </div>
        
    </form>
    <div class="loginBottom">

    </div>
</div>
<script src="js/libs/jquery/jquery.min.js"></script>
<script src="js/libs/form/jquery.validate.min.js"></script>
<script>
$('.loginForm').validate({
    rules:{
        username:{
            required: true
        },
        password:{
            required: true,
            rangelength: [6,8]
        }
        
    },
    messages:{
        username:{
            required: "用户名不能为空",
        },
        password:{
            required: "密码不能为空",
            rangelength: "密码需为6-8位"
        }
    }
});
</script>       
</body>
</html>