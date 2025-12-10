<%@ page contentType="text/html; charset=UTF-8"%>
<%
	//JSP는 서블릿에서 개발자가 직접 자료형을 이용하여 사용해야 할 객체들을 좀더 편리하게 사용할 수 있도록 
	// 내장객체를 지원함 , 따라서 개발자는 자료형을 명시하지 않고, 이미 정해진 객체의 변수명을 사용할 수 있다.
	//out, request, response, session, application
	//application 내장객체 - 애플리케이션의 전역적 정보를 가진 객체 (서블릿에서의 자료형은 ServletContext 이다.)
	//								생명주기 - 톰켓을 켤때부터 끌때까지 .. 강력한 객체, 톰캣과 생명력이 같음
	//								JavaEE에서 데이터를 담을 수 있는 객체 중 가장 생명력이 길다..
	//session 내장객체 - 클라이언트의 세션 쿠키가 유효한 동안, 서버에서 정해놓은 일정 시간 동안 재요청이 없을때 까지..
	// 						 주로 로그인 인증에 많이 사용됨
	//request 내장객체 - 요청이 들어와서 응답이 처리될때까지만 생명을 유지함 
	
	application.setAttribute("born", "진주");
	session.setAttribute("id", "yeon");
	request.setAttribute("hobby", "잠");
	
	
%>

<a href="/test/result.jsp">결과 페이지 전송</a>"