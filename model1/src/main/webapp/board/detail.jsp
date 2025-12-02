<%@page import="com.ch.model1.dto.BoardDTO"%>
<%@page import="com.ch.model1.repository.BoardDAO"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<meta charset="UTF-8">
<%!
	BoardDAO boardDAO = new BoardDAO();
%>
<%
	//list.jsp로부터 전송되어온 파라미터인 board_id 의 값을 이용하여 DAO 에게 일 시키자
	//select* from board where board_id=?;
	String board_id= request.getParameter("board_id");
	BoardDTO boardDTO = boardDAO.select(Integer.parseInt(board_id));
%>
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
	
	//서머 노트 연동
	$(function() {
		$("#summernote").summernote({
			placeholder : "내용을 입력하세요",
			height : 250
		});

		//서머노트에 동적으로 데이터 넣기
		$("#bt_edit").click(function(){
			$("form").attr({
				action:"/board/edit",
				method:"post"
			});
			
			$("form").submit();
		});
	
		
		$("#bt_del").click(function() {
			if(confirm("삭제하시겠어요?")){
				$("form").attr({
					action:"/board/delete",
					method:"post"// 이미 form 태그에 hidden 값으로 파라미터가 있기 때문에, post 방식으로 전송하자.
				});
				$("form").submit();				
			}
		});
		
		$("#bt_list").click(function() {
			location.href = "/board/list.jsp";
		});
	});

	//글 쓰기와 목록 버튼 이벤트 연결
</script>
</head>
<body>
	<h3>Detail Form</h3>

	<div class="container">
		<form>
			<input type="hidden" name="board_id"  value = "<%=boardDTO.getBoard_id()%>"> 
			<label for="fname">제목</label>
			 <input type="text" id="title" name="title"  value = "<%=boardDTO.getTitle()%>"> 
			 <label for="lname">작성자</label> 
				<input type="text" id="writer" name="writer"  value = "<%=boardDTO.getWriter()%>">
			<label for="lname">글 내용</label> 
			<textarea id="summernote" name="content"  style="height: 200px"><%=boardDTO.getContent()%></textarea>

			<input type="button" value="글 수정" id="bt_edit"> 
			<input type="button" value="글 삭제" id="bt_del">
			<input type="button" value="글 목록" id="bt_list">
		</form>
	</div>

</body>
</html>
