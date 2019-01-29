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
	记住我：<input type="checkbox" name="remember-me" value="true"/><br/>
	<input type="submit" value="登录"/>
</form>


<h3>短信登录</h3>
	<form action="/doLoginWithSmscode" method="post">
		<table>
			<tr>
				<td>手机号:</td>
				<td><input type="text" name="mobile" value="13012345678"></td>
			</tr>
			<tr>
				<td>短信验证码:</td>
				<td>
					<input type="text" name="smsCode">
					<a href="/smsCode?mobile=13012345678">发送验证码</a>
				</td>
			</tr>
			<tr>
				<td colspan="2"><button type="submit">登录</button></td>
			</tr>
		</table>
	</form>
	<br>
	<h3>社交登录</h3>
	<a href="/qqLogin/callback.do">QQ登录</a>
	&nbsp;&nbsp;&nbsp;&nbsp;
	<a href="/qqLogin/weixin">微信登录</a>

 <script type="text/javascript">
        
        // 刷新验证码
        function changeCode() {
            var time = new Date().getTime();
            $("#kaptchaImage").attr('src', '/captchaImage?id='+time);
        }

    </script>
</body>
</html>