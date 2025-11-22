<%@page import="java.sql.Connection"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.DriverManager"%>
<%@ page import = "java.sql.PreparedStatement"    %>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%!
	Connection con;
	PreparedStatement pstmt;
	ResultSet rs;
	
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String user = "servlet";
	String pwd = "1234";
%>
<%

		Class.forName("oracle.jdbc.driver.OracleDriver"); // 1) 드라이버 로드 
		con = DriverManager.getConnection(url,user,pwd);// 2) 접속
		String sql = "select* from website";// 3) 쿼리 실행
		pstmt = con.prepareStatement(sql); // 쿼리 수행 객체 생성
		rs=pstmt.executeQuery();// 쿼리 수행 후 그 결과를 rs 로 받자.!! (커서는 맨꼭대기)<표>

%>


<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<style>
table {
  border-collapse: collapse;
  border-spacing: 10px;
  width: 80%;
  border: 1px solid #ddd;
}

th, td {
    padding: 10px 15px;       /* 좌우/상하 여백 통일 */
    text-align: left;
    vertical-align: middle;   /* 세로 중앙 정렬 */
    font-weight: normal;      /* th와 td 글꼴 두께 맞춤 */
}

tr:nth-child(even) {
  background-color: #f2f2f2;
}

td img {
    max-width: 800px;   /* 가로 크기 제한 */
    max-height: 800px;  /* 세로 크기 제한 */
    vertical-align: middle; /* 텍스트와 세로 중앙 정렬 */
    
}
.preview-text {
    position: relative;
    cursor: pointer;
}

.preview-img {
    display: none;           /* 기본적으로 숨김 */
    position: absolute;
    top: -10px;
    left: 110%;
    width: 100%;            /* 미리보기 크기 */
    height: auto;
    border: 1px solid #ccc;
    background: #fff;
    z-index: 10;
}

.preview-text:hover .preview-img {
    display: block;          /* 마우스 올리면 표시 */
}
</style>
</head>
<body>

<h2>Zebra Striped Table</h2>

<table>
  <tr>
    <th>chart_id</th>
    <th>ID</th>
    <th>noticePic</th>
    <th>환자명</th>
  </tr>
  <%while(rs.next()){ %>
  <tr>
    <td><%out.print(rs.getInt("website_id")); %></td>
    <td><%out.print(rs.getString("title")); %></td>
	<td>
	    <a href="/FileDownloadServlet?name=<%= rs.getString("filename") %>" target="_blank">
	        <span class="preview-text"><%= rs.getString("filename") %>
	            <img class="preview-img" src="/FileDownloadServlet?name=<%= rs.getString("filename") %>"/>
	        </span>
	    </a>
	</td>
    <td><%out.print(rs.getString("cusname")); %></td>
  </tr>
  <%} %>

</table>

</body>
</html>
