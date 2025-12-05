<%@page import="com.ch.model1.dto.News"%>
<%@page import="com.ch.model1.repository.NewsDAO"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%!
	NewsDAO dao = new NewsDAO();
%>
<%
	String news_id = request.getParameter("news_id");
	News news=dao.select(Integer.parseInt(news_id));

%>
<meta charset="UTF-8">
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<style>
body {
	font-family: Arial, Helvetica, sans-serif;
}

* {
	box-sizing: border-box;
}

input[type=text], select, textarea {
	width: 100%;
	padding: 12px;
	border: 1px solid #ccc;
	border-radius: 4px;
	box-sizing: border-box;
	margin-top: 6px;
	margin-bottom: 16px;
	resize: vertical;
}

input[type=button] {
	background-color: #04AA6D;
	color: white;
	padding: 12px 20px;
	border: none;
	border-radius: 4px;
	cursor: pointer;
}

input[type=button]:hover {
	background-color: #45a049;
}

.container {
	border-radius: 5px;
	background-color: #f2f2f2;
	padding: 20px;
}
</style>
<!-- include libraries(jQuery, bootstrap) -->
<link
	href="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css"
	rel="stylesheet">
<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script
	src="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>

<!-- include summernote css/js -->
<link
	href="https://cdn.jsdelivr.net/npm/summernote@0.9.0/dist/summernote.min.css"
	rel="stylesheet">
<script
	src="https://cdn.jsdelivr.net/npm/summernote@0.9.0/dist/summernote.min.js"></script>
<script>

function registComment(){
	let xhttp = new XMLHttpRequest();
	
	xhttp.open("POST", "/news/comment_regist.jsp");	// 시간 관계상 jsp
	
	let msg = $("input[name='msg']").val();
	let reader = $("input[name='reader']").val();
	
	// 비동기적으로 POST 요청을 하려면, 기존에 브라우저가 자동으로 해 주었던 x-www-form-urlencoded를 직접 지정해야 한다..
	// 아래의 헤더 속성은 Form 전송 시 브라우저가 원래 자동으로 해줬던 것임...
	xhttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
	
	xhttp.send("msg="+msg+"&reader="+reader);// 서버에 요청 시작
}

	//서머 노트 연동
	$(function() {
		$('#summernote').summernote({
			placeholder : "내용을 입력하세요",
			height : 250
		});
		//서머노트에 동적으로 값 채워 넣기
		$('#summernote').summernote("code","<%=news.getContent()%>")
	});
	

	//글 쓰기와 목록 버튼 이벤트 연결
</script>
</head>
<body>
	<h3>Contact Form</h3>

	<div class="container">
		<form>
			<label for="fname">제목</label>
			 <input type="text" id="fname" name="title" value="<%=news.getTitle() %>"> 
			 <label for="lname">작성자</label> 
				<input type="text" id="lname" name="writer" value="<%=news.getWriter() %>">
			<label for="lname">글 내용</label> 
			<textarea id="summernote" name="content"
				placeholder="Write something.." style="height: 200px"></textarea>

			<input type="button" value="글 등록" id="bt_regist"> 
			<input type="button" value="글 목록">
		</form>
		  <div>
		  	<input type="text" style="width: 65%; background:#cccccc;" name="msg" placeholder = "댓글 내용">
		  	<input type="text" style="width: 20%; background:green;" name="reader" placeholder = "작성자">
		  	<input type="button" value="댓글 등록" onClick="registComment()">
		  </div>
	</div>

</body>
</html>
