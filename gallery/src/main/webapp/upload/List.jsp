<%@page import="java.sql.Connection"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.DriverManager"%>
<%@ page import = "java.sql.PreparedStatement"    %>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	//page 지식 영역에서 contentType() 명시한 것은, 이 jsp가 서블릿으로 변환되어 질때
	// response 객체의 메서드 중 setContentType("text/html;charset=utf8")
%>
<%!
	//선언부
	//jsp 는 사실상 서블릿이다.
	//결론 : jsp의 개발목적 - 서블릿이 디자인을 표현하는데 너무나 비효율적이므로,
	// 개발자 대신 디자인 컨텐츠를 시스템인 웹컨테이너가 대신 작성해주기 위한 스크립트 언어
	Connection con;
	PreparedStatement pstmt;
	ResultSet rs;
	
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String user = "servlet";
	String pwd = "1234";
%>
<%
		//이 영역을 스크립틀릿 영역이라 하며, 추후 고양이에 의해 이 jsp 가 서블릿으로 반환되어 질때
		//이 영역에 작성한 코드는 service()안에 작성한 것과 같아진다. *************
		//선언한 적도 없는 레퍼런스 변수를 사용할 수 있는 이유? ************
				
		//선언한 적도 없는 레퍼런스를 변수를 사용할 수 있는 이유??
		//JSP는 총 9가지 정도의 내장객체를 지원함 (Built-in Object)
		//문자 기반의 출력 스트림 객체를 미리 변수명까지 지정해 놓음 out 이라 함
 		
		//오라클 연동하기
		//아래의 코드는 순수 자바 코드에서 작성할경우 예외처리 강제
		//But  jsp영역은 실행직전 tomcat에 의해 서블릿으로 변환되어 지며 특히 스크립틀릿 영역은
		//service() 메서드로 코드가 작성되고, 이때 tomcat 이 예외처리 까지 해버렸으므로 jsp에서는 
		//예외처리가 강제되지 않음
		Class.forName("oracle.jdbc.driver.OracleDriver"); // 1) 드라이버 로드 
		con = DriverManager.getConnection(url,user,pwd);// 2) 접속
		String sql = "select* from gallery";// 3) 쿼리 실행
		pstmt = con.prepareStatement(sql); // 쿼리 수행 객체 생성
		
		// 쿼리문이 select인 경우, 그 결과표를 받는 객체가 ResultSet
		rs=pstmt.executeQuery();// 쿼리 수행 후 그 결과를 rs 로 받자.!! (커서는 맨꼭대기)<표>

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
</style>
</head>
<body>
<pre>
	1) JSP란? Java Server Page (오직 JavaEE 기반의 서버에서만 해석 및 실행됨)
	장점 - 서블릿과 달리 HTML 을 혼용하여 사용이 가능(서블릿의 디자인 표현의 취약점 보완하기 위한 기술)
	2) JSP의 코드 작성 영역
		ㄴ JSP는 다음 3가지 영역에 코드를 작성할 수 있다.
		1) 지시역역 - <%  %> 안에 @: 현재 JSP 페이지의 인코딩,파일 유형,다른 클래스의 위치(import)등을 위한 영역
		2) 선언부 - <%! %> !가 붙은 멤버영역 : 멤버 변수, 멤버 메서드 등
		3) 스크립틀릿 -<% %> 실행 영역 : 실직적으로 로직을 작성하는 영역 . 
</pre>
<h2>Zebra Striped Table</h2>
<p>For zebra-striped tables, use the nth-child() selector and add a background-color to all even (or odd) table rows:</p>

<table>
  <tr>
    <th>First Name</th>
    <th>Last Name</th>
    <th>Points</th>
  </tr>
  <%
  // rs 객체의 next()메서드를 호출할때마다, 커서가 밑으로 한칸씩 이동하는데
  // 커서가 위치한 행의 레코드가 존재할 경우 true, 없을경우 false
  %>
  <%while(rs.next()){ %>
  <tr>
    <td><%out.print(rs.getInt("gallery_id")); %></td>
    <td><%out.print(rs.getString("title")); %></td>
    <td><%out.print(rs.getString("filename")); %></td>
  </tr>
  <%} %>
</table>

</body>
</html>
<%
	rs.close();
	pstmt.close();
	con.close();
%>