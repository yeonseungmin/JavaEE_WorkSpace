<%@page import="javax.print.attribute.HashPrintRequestAttributeSet"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<meta charset="UTF-8">
<%
	/*
		클라이언트가 비동기적으로 요청을 시도하므로, 파라미터를 받고, DB에 넣은 후
		응답정보는 html(X) vs json(O)
		
		
	*/
 	request.setCharacterEncoding("utf-8");
	String msg= request.getParameter("msg");
	String reader= request.getParameter("reader");
	
	System.out.println("msg는" + msg);
	System.out.println("msg는" + reader);
%>