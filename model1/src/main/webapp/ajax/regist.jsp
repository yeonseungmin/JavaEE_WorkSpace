<%@ page contentType="text/html; charset=UTF-8"%>
<%
	//스크립틀릿 (이 jsp 가 서블릿으로 변환될때 service(request, response)메서드 영역)
	// 넘어온 파라미터를 받아서 mysql의 member2 테이블에 insert!!
	
	//jsp에서는 개발자가 요청객체, 응답 객체를 별도로 변수명을 바꿀수 없다.
	//이유? 이미 결정되어 있다..(내장객체라 한다) built-in object
	request.setCharacterEncoding("utf-8");
	String id = request.getParameter("id");
	String name = request.getParameter("name");
	String email = request.getParameter("email");
	
	//PrintWriter 조차도 이미 내장객체로 지원하므로, 명칭은 out으로 정해져 있다. jsp!!
	out.print("id="+id+"<br>");
	out.print("name="+name+"<br>");
	out.print("email="+email+"<br>");
	
	
%>