<%@page import="com.ch.model1.dto.Member2DTO"%>
<%@page import="java.util.List"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@page import="com.ch.model1.repository.Member2DAO" %>
<%!
	Member2DAO dao = new Member2DAO();
%>
<%
	//페이지 로드하자마자 들어있는 데이터를 표현해 주는 jsp

	//클라이언트의 비동기적 요청이 들어오면, 서버는 HTML(X) 데이터(O)를 보내야한다.
	//목록 가져오기 
	List<Member2DTO> list=dao.selectAll();

	//클라이언트가 이해할 수 있는 데이터 형식으로 응답, 여기서는 클라이언트가 웹브라우저 이므로
	//JSON으로 응답하겠다.(JSON은 중립적 문자열 이기 때문에, 각종 디바이스에서 이해할 수 있는 형식의 데이터이다.)
	
	//아래의 JSON 문자열을 말 그대로 문자열 이므로 자바는 그냥 String 으로 이해		
	StringBuffer data =new StringBuffer();
	
	data.append("[");
	for(int i=0; i<list.size();i++){
		Member2DTO obj = list.get(i);
		data.append("{");
		data.append("\"member2_id\":"+obj.getMember2_id()+" ,");
		data.append("\"id\":\""+obj.getId() +"\",");
		data.append("\"name\":\""+obj.getName() +"\",");
		data.append("\"email\":\""+obj.getEmail()+"\"");
		data.append("}");
		if(i+1 < list.size()){
		data.append(",");
		}
	}
	data.append("]");
	System.out.println(data.toString());
	
	out.print(data.toString());//클라이언트인 웹브라우저에게 보내기
	
%>