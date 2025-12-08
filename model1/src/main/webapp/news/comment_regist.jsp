<%@page import="com.ch.model1.dto.News"%>
<%@page import="com.ch.model1.dto.Comment"%>
<%@page import="com.ch.model1.repository.CommentDAO"%>
<%@page import="javax.print.attribute.HashPrintRequestAttributeSet"%>
<%@ page contentType="application/json; charset=UTF-8"%>
<meta charset="UTF-8">
<%! CommentDAO commentDAO = new CommentDAO(); %>
<%
	/*
		클라이언트가 비동기적으로 요청을 시도하므로, 파라미터를 받고, DB에 넣은 후
		응답정보는 html(X) vs json(O)
		
		
	*/
 	request.setCharacterEncoding("utf-8");
	String msg= request.getParameter("msg");
	String reader= request.getParameter("reader");
	String news_id = request.getParameter("news_id");//제일 중요 ~~ 부모의 Pk
	
	Comment comment = new Comment();
	comment.setMsg(msg);
	comment.setReader(reader);
	//부모가 숫자가 아닌 객체형으로 보유하고 있으므로
	News news = new News();
	news.setNews_id(Integer.parseInt(news_id));
	comment.setNews(news);
	
	System.out.println("msg는" + msg);
	System.out.println("reader는" + reader);
	System.out.println("news_id는" + news_id);
	
	int result = commentDAO.insert(comment);
	System.out.println("등록결과"+result);
	
	//결과 처리
	//클라이언트는 비동기로 요청을 시도했기 때문에 서버측에서 만일 완전한 html로 응답을 해버리면, 클라이언트의 의도와는 달리
	//동기 방식을 염두해둔 응답 정보임으로, 서버측에서는 순수 데이터 형태로 응답 정보를 보내야 한다. . 이떄 압도적으로 많이 사요되는 형식의
	//데이터는 JSON 이다.(이유 : json는 그냥 문자열이기 때문에 모든 시스템(linux, mac, android, ios 상관없이 시스템 중립적))
	if(result <1){
		out.print("{\"resultMsg\":\"등록실패\"}");
	}else{
		out.print("{\"resultMsg\":\"등록성공\"}");
	}
	
	
%>