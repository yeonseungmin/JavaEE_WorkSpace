<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.DriverManager"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.sql.DriverManager" %>
<%@page import="java.sql.Connection" %>
<%!	// 선언부 {이 jsp 가 서블릿으로 변환되어질때, 멤버변수와 멤버메서드가 정의된 영역}
	String url = "jdbc:mysql://localhost:3306/java";
	String user = "servlet";
	String password ="1234";

	Connection con;
	PreparedStatement pstmt;
	ResultSet rs;//select 수행후 표를 담아 제어할수 있는 객체
%>
<%
//jsp는 서블릿이므로, 이 영역(스클립틀릿) 에서 개발자가 코드를 작성하면, 이jsp가 Tomcat에 의해 서블릿으로 변환되어 질때
// 생명주기 (init,service, destory) 중 service() 메서드 영역에 코드를 작성한 것으로 처리
//따라서 클라이언트의 요청을 처리하는 매서드인 service() 메서드에서 mysql의 데이터를 가져와 화면에 출력;
//주의) 서블릿으로도 가능은 하지만 , 수 많은 코드 라인마다 out.print() 출력해야 하므로, 디자인 작업시 효율성이 떨어짐
	Class.forName("com.mysql.cj.jdbc.Driver");//mysql 드라이버
	con =DriverManager.getConnection(url,user,password);
	String sql="select* from notice";
	//(sql,스크롤이가능한옵션, 오직 읽지전용)
	pstmt=con.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
	//DML이 아닌 select 이기 때문에, 메서드 executeQuery()를 사용
	rs = pstmt.executeQuery();
	
%>
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
/* 링크 밑줄 제거 */
a{text-decoration:none}
</style>
</head>
<body>

	<h2>Zebra Striped Table</h2>
	<p>For zebra-striped tables, use the nth-child() selector and add a
		background-color to all even (or odd) table rows:</p>

	<table>
		<tr>
			<th>No</th>
			<th>제목</th>
			<th>작성자</th>
			<th>등록일</th>
			<th>조회수</th>
		</tr>
		<%
			rs.last();
			out.print("현재레코드의 총 수는 "+ rs.getRow());
			//rs의 기본 속성은 ResultSet.TYPE_FORWARD_ONLY 로 되어있음
			//TYPE_FORWARD_ONLY 상수로 지정되면, 커서가 오직 전방향으로 한칸씩만 이동가능
			//PrepareStatement 생성시 상수를 지정해야함
			
			//마지막으로 이동한 rs 커서를 다시 원상복귀
			rs.beforeFirst();
		%>
		<%
			while(rs.next()){
		%>
		<tr>
		<%//<a href="/notice/detail.jsp?변수명=값 %>
			<td><%=rs.getInt("notice_id") %></td>
			<td><a href="/notice/detail.jsp?notice_id=<%=rs.getInt("notice_id")%>"><%=rs.getString("title")%></a></td>
			<td><%=rs.getString("writer")%></td>
			<td><%=rs.getString("regdate")%></td>
			<td><%=rs.getInt("hit")%></td>
		</tr>
		<%} %>
		<tr>
			<td colspan="5">
				<button onClick="location.href='/notice/regist.jsp'">글 등록</button>
			</td>
		</tr>
	</table>

</body>
</html>
<%
rs.close();
pstmt.close();
con.close();
%>

