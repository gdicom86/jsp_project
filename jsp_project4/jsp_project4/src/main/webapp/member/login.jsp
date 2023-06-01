<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login Page</title>
</head>
<body align = "center" style= "background-image: url('/img/pixel-art.jpg'); background-repeat: no-repeat; background-size: cover; height: 100vh; background-attachment: fixed;" >
	<h1>LogIn Page</h1>
	<form action="/mem/login_auth" method="post">
		ID: <input type="text" name="id"><br>
		Password: <input type="password" name="password"><br>
		<button type="submit">Log In</button>
	</form>
</body>
</html>