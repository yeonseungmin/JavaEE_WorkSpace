<%@page import="com.ch.mybatisapp.dto.Board"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@page import="com.ch.mybatisapp.repository.BoardDAO" %>
<% BoardDAO boardDAO = new BoardDAO(); %>
<% 
	//파라미터를 넘겨받아 게시물 1건 등록
	request.setCharacterEncoding("utf-8");
	
	String title = request.getParameter("title");
	String writer = request.getParameter("writer");
	String content = request.getParameter("content");
	
	Board board = new Board();
	
	board.setTitle(title);
	board.setWriter(writer);
	board.setContent(content);
	
	int result = boardDAO.insert(board);
	if(result<1){
		out.print("연결실패");
	}else{
		out.print("연결성공");
	}
	
%>