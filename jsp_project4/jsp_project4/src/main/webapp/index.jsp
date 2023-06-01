<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Index</title>
</head>
<body align = "center" style= "background-image: url('/img/pixel-art.jpg'); background-repeat: no-repeat; background-size: cover; height: 100vh; background-attachment: fixed;" >
	<h1>Index page</h1>
	<c:if test="${ses.id ne null}">
		${ses.id }님이 login 하였습니다. <br>
		계정생성일: ${ses.reg_date }<br>
		마지막접속: ${ses.last_login }<br>
	</c:if>

	<a href="/mem/modify"><button>회원정보수정</button></a>
	<a href="/mem/join"><button>join</button></a>
	
	<c:choose>
		<c:when test="${ses.id ne null }">
			<a href="/mem/logout"><button>logout</button></a> 
			<a href="/mem/list"><button>list</button></a>
			<a href="/brd/register"><button>게시글 등록</button></a> <br>
			<a href="/brd/page"><button>게시글 목록</button></a>
		</c:when>
		<c:otherwise>
			<a href="/mem/login"><button>login</button></a> 
		</c:otherwise>
	</c:choose>

	<script type="text/javascript">
		const msg_login = `<c:out value="${msg_login}" />`;
		console.log(msg_login);
		if(msg_login === '0') {
			alert('로그인 실패!');
		}
	</script>
</body>
</html>