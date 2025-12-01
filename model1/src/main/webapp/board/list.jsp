<%@page import="com.ch.model1.dto.BoardDTO"%>
<%@page import="java.util.List"%>
<%@page import="java.sql.ResultSet"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@page import="com.ch.model1.repository.BoardDAO"%>
<%@ page import ="java.sql.ResultSet" %>
<%!
	//이 영역은 선언부 이기 때문에 이 jsp가 추후 서블릿으로 변경될때의 멤버영역
	BoardDAO boardDAO = new BoardDAO(); 
	
%>
<%
	// 이 영역은 스크립틀릿 이기 때문에 jsp 파일이 서블릿으로 변환될때 service() 메서드 영역
	// 얼마든지 DB연동이 가능하긴함..
	// But 디자인 코드와 데이터베이스 연동코드가 하나로 합쳐지면(스파게티 코드)가 되기에
	// 추후 DB 연동코드를 재사용하기 힘들다..
	List<BoardDTO> list = boardDAO.selectAll();
	out.print("컬랙션 수"+list.size());
%>
<meta charset="UTF-8">
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<style>
table {
  border-collapse: collapse;
  border-spacing: 0;
  width: 100%;
  border: 1px solid #ddd;
}

th, td {
  text-align: left;
  padding: 16px;
}

tr:nth-child(even) {
  background-color: #f2f2f2;
}
</style>

</head>
<body>

<table>
  <tr>
    <th>No</th>
    <th>title</th>
    <th>writer</th>
    <th>regdate</th>
    <th>hit</th>
  </tr>
  <%
  	//rs에 들어있는 레코드들을 한칸씩 이동하며 꺼내자.
  	//rs.next() 가 true 인 경우
  	
  		for(int i=0; i<list.size(); i++){
  			BoardDTO boardDTO =list.get(i);
  %>
  <tr>
    <td><%= boardDTO.getBoard_id() %></td>
    <td><%= boardDTO.getTitle() %></td>
    <td><%= boardDTO.getWriter() %></td>
    <td><%= boardDTO.getRegdate() %></td>
    <td><%= boardDTO.getHit() %></td>
  </tr>
  <%} %>
</table>

</body>
</html>
