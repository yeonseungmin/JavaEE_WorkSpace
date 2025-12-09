<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h3>당신의 선택한 음식 결과에 대한 메세지</h3>
	<%
		//jsp의 내장객체(request,response session.out 등이있다.)
		//session 은 메모리 낭비가 심함.
		String msg=(String)request.getAttribute("msg");
	%>
	<%=msg %>
	
</body>
</html>