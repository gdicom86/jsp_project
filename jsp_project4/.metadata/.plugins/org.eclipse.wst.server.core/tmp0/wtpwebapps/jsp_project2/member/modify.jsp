<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Modify Page</title>
</head>
<body align = "center" style= "background-image: url('/img/pixel-art.jpg'); background-repeat: no-repeat; background-size: cover; height: 100vh; background-attachment: fixed;" >
	<h3>modify 제품 수정 페이지</h3>
	
	<br>
	
	<form action="/mem/edit" method="post">
		ID : <input type="text" name="id" value="${ses.id }" readonly="readonly"> <br> 
		Password : <input type="text" name="password" value="${ses.password }"> <br>
		나이 : <input type="text" name="age" value="${ses.age }"> <br>
		이메일 : <input type="text" name="email" value="${ses.email }" > <br>
		등록일자 : <input type="text" name="reg_date" value="${ses.reg_date }" readonly="readonly"> <br>
		<input type="hidden" name="last_login" value="${ses.last_login }"><br>
		<button type="submit">수정하기</button>
		<a href="/"><button type="button">취소하기</button></a>
	</form>
	<form action="/mem/delete" method="post">
		<input type="hidden" name="id" value="${ses.id }">
		<button type="submit">회원탈퇴</button>
	</form>
</body>
</html>