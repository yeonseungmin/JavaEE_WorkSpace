
<%@page import="com.ch.shop.dto.Board"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%
	//DetailController가 저장해준 request에 저장해준 Board를 꺼내자!!
	Board board =(Board)request.getAttribute("board");
%>
<!DOCTYPE html>
<html lang="en">
<head>
  <title>Bootstrap Example</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
<link href="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css" rel="stylesheet">
<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>

<!-- include summernote css/js -->
<link href="https://cdn.jsdelivr.net/npm/summernote@0.9.0/dist/summernote.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/summernote@0.9.0/dist/summernote.min.js"></script>
<script>
	$(()=>{
		$('#summernote').summernote();
		
		$('#summernote').summernote("code","<%=board.getContent()%>");
		
	
		
		$("#bt_edit").click(()=>{
			if(confirm("수정하시겠어요?")){
					$("#form1").attr({
						action:"/board/edit"
						//method:"post"		// << 여기 post 옆에 세미콜론 붙여놨더라. 오타난거 수정하고 위에 action에 링크 넣어놨다. 잘 된다 이제. 그리고 키보드 마우스좀 닦아라 개기름 아으
					});
					$("#form1").submit();
				}
			
			});
		
		$("#bt_del").click(()=>{
			
			if(confirm("삭제하시겠어요?")){
				//delete from board where board_id=?;
				location.href="/board/delete.do?board_id=<%=board.getBoard_id()%>";
				}
			});
		
		$("#bt_list").click(()=>{
			location.href="/board/list";
			});

	});
		
		

</script>
</head>
<body>

<div class="container">
  <h2>Stacked form</h2>
  <form action="/action_page.php" id = "form1">
  <input type="hidden" name ="board_id" value ="<%=board.getBoard_id()%>">
    <div class="form-group">
      <label for="email">제목:</label>
      <input type="email" class="form-control" id="email" placeholder="Enter email" value = "<%=board.getTitle() %>" name="title">
    </div>
    <div class="form-group">
      <label for="pwd">작성자:</label>
      <input type="password" class="form-control" id="pwd" placeholder="Enter password" value = "<%=board.getWriter() %>"  name="writer">
    </div>
    <div class="form-group">
      <label for="pwd">내용:</label>
      <textarea id="summernote"  class="form-control" id="pwd" placeholder="Enter password"  name="content"></textarea>
    </div>

    <button type="button" id = "bt_edit" class="btn btn-primary">수정</button>
    <button type="button" id = "bt_del" class="btn btn-primary">삭제</button>
    <button type="button" id = "bt_list" class="btn btn-primary">목록</button>
   
  </form>
</div>

</body>
</html>
