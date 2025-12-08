<%@page import="com.fasterxml.jackson.databind.ObjectMapper"%>
<%@page import="com.ch.model1.dto.Comment"%>
<%@page import="java.util.List"%>
<%@page import="com.ch.model1.repository.CommentDAO"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%! CommentDAO commentDAO = new CommentDAO(); %>
<%
	//특정 뉴스 기사에 딸려있는 코멘트 게시물 모두 가져오기
	// select* from comment where news_id=?
	String news_id = request.getParameter("news_id");
	//결과로 반환된 리스트를 클라이언트에게 전송시, 순수 데이터 형태로 보내주자 
	//클라이언트는 동기방식인 페이지 새로고침보다 비동기방식인 순수한데이터를 원함.
	List<Comment> commentList = commentDAO.selectByNewsId(Integer.parseInt(news_id));
	/* 아래와 같이 그냥 List 의 레퍼런스 변수 자체를 출력해 버리면, 클라이언트가 원하는 파싱 대상이 되는 JSON 형태가 아니므로, 파싱 자체가 불가능하다..  */
	/* out.print(commentList); */
	
	//해결책? 클라이언트가 해석 가능한 형태의 문자열인 JSON 으로 전송해야 한다.
			
	//아래의 모든 객체를 문자열로 보아놓기 위한 StringBuffer
/* 	StringBuffer sb = new StringBuffer();
	/* {
		"comment_id":13,
		"msg":"qwe",
		"writedate":"2022"
	}, */
	
	
/* 	for(int i =0; i<commentList.size(); i++){
		Comment comment = commentList.get(i);
		comment.getComment_id();
	}  */

	//해결책 : 개발자가 직접 List 안에있는 DTO 들을 꺼내어 json문자열로 변환하는 작업은 너무나 비효율적이다. 실수 가능성이 높다.
	//따라서 외부 라이브러리를 적극 활용하자. (참고로 스프링 프레임 웤에서는 내장하고 있는 기능이다.)	Jackson 라이브러리
	//jackson 라이브러리르 ㄹ활용하면 객체와 JSON 문자열 간 변환을 자동으로 처리해줌 (js에서도 jackson과 비슷한 용도의 내장객체가 있다. JSON 내장객체)
	ObjectMapper objectMapper = new ObjectMapper();
	
	//네트워크 상에서는 데이터를 전송하려면 반드시 문자열로 변환해야하므로 , 객체를 문자열로 바꾸자
	String result=objectMapper.writeValueAsString(commentList);
	out.print(result);
	
	
%>

