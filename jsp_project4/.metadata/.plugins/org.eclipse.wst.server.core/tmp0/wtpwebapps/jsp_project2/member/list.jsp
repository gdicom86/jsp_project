<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body align = "center" style= "background-image: url('/img/pixel-art.jpg'); background-repeat: no-repeat; background-size: cover; height: 100vh; background-attachment: fixed;" >
	<h1>회원정보 List</h1>
	<table border="1">
		<thead>
			<tr>
				<th>ID</th>
				<th>email</th>
				<th>Age</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${list }" var="mvo">
			<tr>
				<td>${mvo.id }</td>
				<td>${mvo.email }</td>
				<td>${mvo.age }</td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
</body>
</html>