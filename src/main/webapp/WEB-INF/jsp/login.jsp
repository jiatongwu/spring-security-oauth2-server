<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<script src="/testAjax/jquery2.2.4.js"></script>
</head>
<body>
login.jsp
<form action="/doLogin" method="post">
	用户名：<input name="username" type="text"/><br/>
	密码：<input name="password" type="password"/><br/>
	图形验证码：<input name="imageCode" type="text" /><br/>
	<img id="kaptchaImage" src="/captchaImage" onclick="javascript:changeCode();"/> <br/>
	<input type="submit" value="登录"/>
</form>

 <script type="text/javascript">
        
        // 刷新验证码
        function changeCode() {
            var time = new Date().getTime();
            $("#kaptchaImage").attr('src', '/captchaImage?id='+time);
        }

    </script>
</body>
</html>