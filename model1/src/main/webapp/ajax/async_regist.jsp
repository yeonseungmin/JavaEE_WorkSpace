<%@page import="org.apache.tomcat.util.json.JSONFilter"%>
<%@page import="java.util.List"%>
<%@page import="com.ch.model1.dto.Member2DTO"%>
<%@page import="com.ch.model1.repository.Member2DAO"%>
<%@ page contentType="text/html; charset=UTF-8"%>

<%!
	Member2DAO dao = new Member2DAO();
%>
<% %>


<%
	System.out.println("클라이언트로의 요청 감지");// Tomcat 로그에 출력되지만 
	//우리의 경우 이클립스 내부 톰켓이므로, 
	request.setCharacterEncoding("utf-8");
	String id=request.getParameter("id");
	String name = request.getParameter("name");
	String email = request.getParameter("email");
	
	System.out.println("id="+id);
	System.out.println("name="+name);
	System.out.println("email="+email);
	
	Member2DTO dto = new Member2DTO();
	dto.setId(id);
	dto.setName(name);
	dto.setEmail(email);
	int result = dao.insert(dto);
	System.out.println(result);
	
	//입력 성공후 페이지 보여주기
	//아래와 같이 비동기 요청에 대해, 응답정보로서 페이지 접속을 일으키는 코드를 작성하게 되면
	//클라이언트의 브라우저가 지정한 URL 로 재접속을 시도하기 때문에,
	//그 재접속의 결과인 html 을 서버로 부터 받게되고 html을 전송받은 브라우저는 해당 html 화면에 렌더링 해버리므로
	//새로고침 효과가 나버림, 즉 새로고침 없는 without reloading 기능이 상실
	//response.sendRedirect("/ajax/main.jsp");
	
	//해결책: 서버에서는 화면전체를 보내지 말고, 데이터만 보내면 클라이언트는 js로 동적 처리
	// 게시물 목록 가져오기!!
	List<Member2DTO> list =dao.selectAll();
	//클라이언트에게 목록 데이터 보내기
	//out.print("id=동훈,name=동훈,email=동훈");
	//out.print("id*동훈&name*동훈&email*동훈");
	//클라이언트에게 응답 정보를 보낼때 , 어차피 모두 문자열로 밖에 방법이 없다.
	//하지만 , 이 문자열을 넘겨받은 클라리언트의 브라우저의 자바 스크립트는 아래와 같은 문자열로 구성되어 있을경우
	//원하는 데이터를 추출하기가 많이 불편하다.
	//REST API 를 다룰것이므로, 추후 REST 서버를 구축하여, 우리의 서버에 요청을 시도하는 다양한 종류의 클라이언트(스마트폰,웹브라우저,자동차,로봇..등등) 들에게
	//				데이터를 제공해 줄 예정, 이때 사용할 데이터 형식은 전세계적으로 xml 또는 JSON 이 압도적이다.
	// 해결책 : 전세계 개발자들이 사용하는 표준 형식(JSON )을 사용하자.
	//		JSON 이란? 문자열 내의 데이터가 유달리 자바스크립트의 객체 리터럴 정의 기법을 따르는 경우, JSON 문자열이라 한다.
			
	//아래의 JSON 문자열을 말 그대로 문자열 이므로 자바는 그냥 String 으로 이해		
	StringBuffer data =new StringBuffer();
	data.append("{");
	data.append("\"name\": \"동훈\",");
	data.append("\"email\": \"똥훈\"");
	data.append("}");

	System.out.println(data.toString());
	
	out.print(data.toString());//클라이언트인 웹브라우저에게 보내기
	// 			{"name": "동훈","email": "똥훈"} 서버가 보내온 데이터는 무조건 문자열이기 때문에 아래의 형ㅅ기이 마치 자바스크립트의
	//													객체 리터럴로 착각될 수 없다. 결론 : 객체가 아니다!!
	//													즉, 객체가 아니므로 속성이라는 것도 없으므로 "."으로접근 불가능하다.
	//해결책 - 어떤 문자열이 JSON표기법을 준수하여 작성되어 있다면, 자바스크립트는 내장 객체인 JSON 내장객체를 이용하여
	//			문자열을 해석하여 실제 자바스크립트 객체 리터럴로 전환해 줄 수 있다.
	



	
%>