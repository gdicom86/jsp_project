<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>글쓰기 페이지</title>
</head>
<body>
	<form action="/brd/insert" method="post" enctype="multipart/form-data">
		title: <input type="text" name="title"> <br>
		writer: <input type="text" name="writer" value="${ses.id }" readonly="readonly"> <br>
		content: <textarea rows="3" cols="30" name="content"></textarea> <br>
		imageFile: <input type="file" id="file" name="image_file" accept="image/png, image/jpeg, image/jpg, image/bmp, image/gif"><br>
		<button type="submit">게시글 등록</button>
	</form>
</body>
</html>