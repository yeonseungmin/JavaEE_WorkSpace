<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<%
		out.print(application.getAttribute("born"));
		out.print(session.getAttribute("id"));
		out.print(request.getAttribute("hobby"));
	%>
</body>
</html>