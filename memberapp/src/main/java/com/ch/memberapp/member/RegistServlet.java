package com.ch.memberapp.member;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ch.memberapp.util.ShaManager;

//회원 가입 요청을 처리하는 서블릿
public class RegistServlet extends HttpServlet{
	String url ="jdbc:mysql://localhost:3306/java";
	String user = "servlet";
	String password ="1234";
	Connection con;
	PreparedStatement pstmt;
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("utf-8");
		
	//넘겨받은 파라미터중 비밀번호의 경우 암호화 시켜서 DB에 넣기
		String id = request.getParameter("id");
		String pwd = request.getParameter("pwd");
		String name = request.getParameter("name");
		
		PrintWriter out = response.getWriter();
		out.print("아이디는" + id +"<br>");
		out.print("비밀번호는" + ShaManager.getHash("pwd") +"<br>");
		out.print("이름는" + name +"<br>");
		
		//드라이버로드
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			try {
				con = DriverManager.getConnection(url,user,password);
				if(con==null) {
					out.print("연결실패");
				}else {
					String sql = "insert into member(id,pwd,name) values(?,?,?)";
					pstmt = con.prepareStatement(sql);
					pstmt.setString(1, id);
					pstmt.setString(2, ShaManager.getHash(pwd));
					pstmt.setString(3, name);
					
					int result = pstmt.executeUpdate();
					
					StringBuffer tag = new StringBuffer();
					tag.append("<script>");
					if(result <1) {
						tag.append("alert('가입실패');");
						tag.append("history.back;");
					}else {
						tag.append("alert('가입 성공');");
						//가입 이메일 발송(어느쪽이든 상관없음 왜? Servlet 특성)
						tag.append("location.href='/member/login.jsp';");
						//가입 이메일 발송(어느쪽이든 상관없음 왜? Servlet 특성)
					}
					tag.append("</script>");
					out.print(tag.toString());//스트림에 스크립트 적용
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			if (pstmt!=null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (con!=null) {
				try {
					con.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		//접속
		
		//쿼리문 수행 insert into member(id,pwd,name) values(?,?,?);
		
		//자원 해제
	}
}
