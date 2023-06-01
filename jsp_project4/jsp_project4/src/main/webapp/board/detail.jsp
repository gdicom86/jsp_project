<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
<!-- JavaScript Bundle with Popper -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+jjXkk+Q2h455rYXK/7HAuoJl+0I4" crossorigin="anonymous"></script>
<title>detail 상세정보</title>
</head>
<body>
<h3>detail 게시판 상세 페이지</h3>
   <table class="table table-hover">
      <tr>
         <th>번호</th>
         <td>${bvo.bno }</td>
      </tr>
      <tr>
         <th>제목</th>
         <td>${bvo.title }</td>
      </tr>
      <tr>
         <th>사용자</th>
         <td>${bvo.writer }</td>
      </tr>
      <tr>
         <th>내용</th>
         <td>
         	<c:if test="${bvo.image_file ne '' && bvo.image_file ne null }">
				<img alt="image" src="/_fileUpload/${bvo.image_file }">
			</c:if>
         	${bvo.content }</td>
      </tr>
      <tr>
         <th>작성일자</th>
         <td>${bvo.reg_date }</td>
      </tr>
      <tr>
         <th>조회수</th>
         <td>${bvo.count }</td>
      </tr>
   </table>
   <a href="/brd/modify?bno=${bvo.bno}"><button type="button">수정</button></a>
   <a href="/brd/remove?bno=${bvo.bno }&img=${bvo.image_file}"><button type="button">삭제</button></a>
   <hr>
   <div>
   comment line<br>

   <input type="text" id="cmtWriter" value="${ses.id }" readonly="readonly">
   <input type="text" id="cmtText" placeholder="Add Comment"> <br>
   <button type="button" id="cmtAddBtn">댓글 등록</button>
   </div><br>

<div class="accordion" id="accordionExample">
  <div class="accordion-item">
    <h2 class="accordion-header" id="headingOne">
      <button class="accordion-button" type="button" data-bs-toggle="collapse" data-bs-target="#collapseOne" aria-expanded="true" aria-controls="collapseOne">
        cno, writer
      </button>
    </h2>
    <div id="collapseOne" class="accordion-collapse collapse show" aria-labelledby="headingOne" data-bs-parent="#accordionExample">
      <div class="accordion-body">
         content, reg_date
      </div>
    </div>
  </div>
</div>
<script type="text/javascript">
	const bnoVal = `<c:out value="${bvo.bno}" />`; //sysout인데 value를 보내기
</script> 
<script src="/resources/board_detail.js"></script>  
<script type="text/javascript">
	printCommentList(bnoVal); 
</script>
</body>
</html>