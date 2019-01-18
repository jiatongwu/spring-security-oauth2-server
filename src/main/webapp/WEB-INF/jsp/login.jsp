<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
login.jsp
<form action="/doLogin" method="post">
	用户名：<input name="username" type="text"/><br/>
	密码：<input name="password" type="password"/><br/>
	<input type="submit" value="登录"/>
</form>
</body>
</html>