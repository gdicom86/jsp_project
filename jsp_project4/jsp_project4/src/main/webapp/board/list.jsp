<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body align = "center" style= "background-image: url('/img/pixel-art.jpg'); background-repeat: no-repeat; background-size: cover; height: 100vh; background-attachment: fixed; color: black;" >
	<h1>게시글 List</h1>
	
	<form action="/brd/page" method="post">
		<div>
		<c:set value="${pgh.pgvo.type }" var="typed"></c:set>
			<select name="type">
			
				<option ${typed == null ? 'selected':'' }>Choose</option>
				<option value='t' ${typed eq 't' ? 'selected':'' }>Title</option>
				<option value='c' ${typed eq 'c' ? 'selected':'' }>Content</option>
				<option value='w' ${typed eq 'w' ? 'selected':'' }>Writer</option>
				<option value='tc' ${typed eq 'tc' ? 'selected':'' }>Title or Content</option>
				<option value='tw' ${typed eq 'tw' ? 'selected':'' }>Title or Writer</option>
			</select>
			<input type="text" name="keyword" placeholder="Search">
			<input type="hidden" name="pageNo" value="${pgh.pgvo.pageNo }">
			<input type="hidden" name="qty" value="${pgh.pgvo.qty }">
			<button type="submit">Search</button>
		</div>
	</form>
	
	<a href="/brd/page?type=w&keyword=${ses.id }">
		<button type="submit" >내가 쓴 글 보기</button>
	</a>
	<div>내 글 총${pgh.totalCount } 개</div>
		<table align = "center" border="1" style = "background-color: white;">
			<thead>
				<tr>
					<th>Board Number</th>
					<th>Title</th>
					<th>Writer</th>
					<th>Register Date</th>
					<th>조회수</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${list }" var="bvo">
					<tr>
						<td>${bvo.bno }</td>
						<td>
						<c:if test="${bvo.image_file ne '' && bvo.image_file ne null }">
							<img alt="image" src="/_fileUpload/th_${bvo.image_file }">
						</c:if>
						<a href="detail?bno=${bvo.bno}">${bvo.title }</a>
						</td>
						<td>${bvo.writer }</td>
						<td>${bvo.reg_date }</td>
						<td>${bvo.count }</td>					
					</tr>
				</c:forEach>
			</tbody>
		</table>

<div style="margin: 32px auto;">
<c:if test="${pgh.prev }">
	<a style="color: white; font-weight: bold;" href="/brd/page?pageNo=${pgh.startPage -1}&qty=${pgh.pgvo.qty}&type=${pgh.pgvo.type}&keyword=${pgh.pgvo.keyword}"> ◁ </a>
</c:if>

<c:forEach begin="${pgh.startPage }" end="${pgh.endPage }" var="i">
<a style="color: white; font-weight: bold;" href="/brd/page?pageNo=${i }&qty=${pgh.pgvo.qty}&type=${pgh.pgvo.type}&keyword=${pgh.pgvo.keyword}">${i } | </a>
</c:forEach>


<c:if test="${pgh.next }">
	<a style="color: white; font-weight: bold;" href="/brd/page?pageNo=${pgh.endPage +1}&qty=${pgh.pgvo.qty}&type=${pgh.pgvo.type}&keyword=${pgh.pgvo.keyword}"> ▷ </a>
</c:if>
</div>
</body>
</html>