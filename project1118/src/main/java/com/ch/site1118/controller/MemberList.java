package com.ch.site1118.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*
 * 오라클에 들어있는 회원의 목록을 가져와서 화면에 출력
 */
public class MemberList extends HttpServlet{
	String url="jdbc:oracle:thin:@localhost:1521:XE";
	String user ="servlet";
	String pass ="1234";
	//클라이언트인 브라우저가 목록을 달라고 요청할 것이기 때문에, doXXX형 메서드 중
	// doGet() 을 재정의 하자
	//클라이언트가 목록을 원하기 때문에..
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//드라이버 로드
		response.setContentType("text/html;charset=utf-8");
//		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		Connection con =null;// 접속 후 그 정보를 가진 객체, 따라서 이 객체가 null인 경우 접속실패
		PreparedStatement pstmt = null;// 쿼리문 수행 객체 , 오직 Connection 객체로 부터 인스턴스 얻음
										// 쿼리문이란, 접속을 전제로 하기 때문..
		ResultSet rs = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			out.print("드라이버로드 성공<br>");
			
			
			try {
				con = DriverManager.getConnection(url,user,pass);
				
				if(con !=null) {
					out.print("접속성공<br>");
					String sql = "select* from member order by member_id asc";
					pstmt = con.prepareStatement(sql);
					
					//DML인 경우 executeUpdate()였지만, select문 인 경우 원격자 서버의 레코드(표)를 네트워크로 
					//가져와야 하므로, 그 표 결과를 그대로 반영할 객체가 필요한데, 이객체를 가리켜 ResultSet이라 한다.
					rs = pstmt.executeQuery();
					//rs는 그냥 표 자체로 생각해도 무방하다. 하지만 rs 내에 존재하는 레코드들을 접근하기 위해서는
					//레코드를 가르키는 포인터 역활을 하는 커서를 제어해야 한다.
					//이 커서는 rs 생성 즉시엔 어떠한 레코드도 가르키지 않은상태이므로 
					// 개발자가 첫번째 레코드를 접근하려면 포인터를 한칸 내려야 한다.
					//rs.next(); //기준보다 한칸 전진
					StringBuffer tag = new StringBuffer();
					tag.append("<table width=\"100%\" border=\"1px\">");
					tag.append("<thead>");
					tag.append("<tr>");
					tag.append("<th>멤버 아이디</th>");
					tag.append("<th>아이디</th>");
					tag.append("<th>비밀번호</th>");
					tag.append("<th>이름</th>");
					tag.append("<th>생성시간</th>");
					tag.append("<th>이메일</th>");
					tag.append("</tr>");
					tag.append("</thead>");
					tag.append("<tbody>");
					while(rs.next()) {
					tag.append("<tr>");
					tag.append("<td>" + rs.getInt("member_id") +"</td>");
					tag.append("<td>" + rs.getString("id") +"</td>");
					tag.append("<td>" + rs.getString("pwd") +"</td>");
					tag.append("<td>" + rs.getString("name") +"</td>");
					tag.append("<td>" + rs.getDate("regdate") +"</td>");
					tag.append("<td>" + rs.getString("email") +"</td>");
					tag.append("</tr>");
					}
					tag.append("</tbody>");
					tag.append("</table>");
					out.print(tag);
					out.print("<a href='/member/join.html'>회원가입</a>");
					
				}else {
					out.print("접속실패<br>");
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			if(rs !=null) {
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}				
			}
			if(pstmt !=null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}				
			}
			if(con !=null) {
				try {
					con.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}				
			}
		}
	}
}
