<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>join 회원가입 페이지</title>
</head>
<body align = "center" style= "background-image: url('/img/pixel-art.jpg'); background-repeat: no-repeat; background-size: cover; height: 100vh; background-attachment: fixed;" >
	<h1>회원가입 페이지</h1>
	<form action="/mem/register" method="post">
		ID : <input type="text" name="id" placeholder="id"> <br>
		Password : <input type="password" name="password" placeholder="password"> <br>
		Email : <input type="text" name="email" placeholder="email"> <br>
		Age : <input type="text" name="age" placeholder="age"> <br>
		<button type="submit">가입완료</button>
	</form>
</body>
</html>