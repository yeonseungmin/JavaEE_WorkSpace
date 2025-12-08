
<%@page import="com.ch.model1.dto.News"%>
<%@page import="com.ch.model1.util.PagingUtil"%>
<%@page import="java.util.List"%>
<%@page import="com.ch.model1.repository.NewsDAO"%>
<%@ page contentType="text/html; charset=UTF-8"%>

<%!//목록 가져오기
	NewsDAO newsDAO = new NewsDAO();
	PagingUtil pgUtil = new PagingUtil();%>
<%
	List<News> newsList = newsDAO.selectAll();
	pgUtil.init(newsList, request);// 이 시점부터 알아서 계산.
	out.print("총 레코드 수는 " + pgUtil.getTotalRecord());
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
  	int curPos = pgUtil.getCurPos();// 페이지당 시작 리스트 내의 인덱스
  	int num = pgUtil.getNum();
  %>
  <%for(int i =0; i<pgUtil.getPageSize(); i++){ %>
  <%if(num<1)break; %>
  <%

  	 News news=newsList.get(curPos++);

  %>
  <tr>
    <td><%= num-- %></td>
    <td><a href="/news/content.jsp?news_id=<%=news.getNews_id()%>"><%= news.getTitle() %></a>
    	<%if(news.getCnt()>0){ %>[<%=news.getCnt()%>]<% }%>
    </td>
    <td><%= news.getWriter() %></td>
    <td><%= news.getRegdate() %></td>
    <td><%= news.getHit() %></td>
  </tr>
  <%} %>
  
  	<td>
  		<button onClick = "location.href='/news/write.jsp'">글 등록</button>
  	</td>
	<td colspan="4"></td>

</table>

</body>
</html>
