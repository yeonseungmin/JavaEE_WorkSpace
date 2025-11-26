<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.Connection"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%!	// 선언부 {이 jsp 가 서블릿으로 변환되어질때, 멤버변수와 멤버메서드가 정의된 영역}
	String url = "jdbc:mysql://localhost:3306/java";
	String user = "servlet";
	String password ="1234";

	Connection con;
	PreparedStatement pstmt;
	ResultSet rs;//select 수행후 표를 담아 제어할수 있는 객체
%>
<%
	Class.forName("com.mysql.cj.jdbc.Driver");
	//위의 페이지 지시 영역은 현재 jsp가 Tomcat에 의해 서블릿으로 코딩되어 질때
	// text/html 부분은 response.setContentType("text/html");
	// charset=UTF-8 response.setCharacterEncoding("utf-8");
	
	//select* from notice where notice_id =2
	//HTTP 통신에서 주고 받는 파라미터는 모두 문자열로 인식된다. 예) 1 -> "1"
	String notice_id = request.getParameter("notice_id");
	out.print("select* from notice where notice_id ="+ notice_id);
	String sql = "select* from notice where notice_id ="+ notice_id;
	con=DriverManager.getConnection(url,user,password);
	pstmt = con.prepareStatement(sql);//쿼리날리기
	rs=pstmt.executeQuery();//쿼리수행
	rs.next();//커서를 before first 위치에서 한칸 이동
	
%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta charset="UTF-8">
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
	background-color: red;
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
<script >
	function del(){
		if(confirm("삭제하시겠어요?")){
			location.href = '/notice/delete?notice_id=<%=rs.getInt("notice_id")%>'	
		}
	}
	function edit(){
		if(confirm("수정하시겠어요?")){
			//작성된 폼 양식을 선버로 전송~~
			let form1 = document.getElementById("form1");
			form1.action ="/notice/edit";//서버의 url
			form1.method="post";
			form1.submit();
		}
	}
</script>
</head>
<body>

	<h3>상세 보기</h3>

	<div class="container">
		<form id="form1">
		<!-- hidden은 개발자의 목적을위해 값은 존재하지만 숨겨놓는 특성 -->
				<input type="hidden" name="notice_id" value="<%=rs.getInt("notice_id")%>" style="background :yellow"> 
				<input type="text" name="title" value="<%=rs.getString("title")%>"> 
				<input type="text" name="writer" value= "<%=rs.getString("writer")%>"> 
				<textarea id="subject" name="content" placeholder="내용 입력" style="height: 200px"><%=rs.getString("content") %></textarea>
			<input type="button" value="수정" onClick="edit()">
			<input type="button" value="삭제" onClick="del()">
			<!-- js 에서 링크를 표현한 내장객체를 location -->
			<input type="button" value="목록" onClick="location.href='/notice/list.jsp'">
		</form>
	</div>

</body>
</html>
<%
	rs.close();
	pstmt.close();
	con.close();
%>
