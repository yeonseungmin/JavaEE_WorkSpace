package com.ch.memberapp.member;

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
import javax.servlet.http.HttpSession;

import org.apache.catalina.tribes.transport.RxTaskPool;
import org.apache.jasper.tagplugins.jstl.core.If;

import com.ch.memberapp.util.ShaManager;

public class LoginServlet  extends HttpServlet{
	Connection con;
	PreparedStatement pstmt;
	ResultSet rs;	//select 문의 결과를 담을 객체
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//유저가 전송한 id, pwd 파라미터값을 이용하여 db와 비교
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		String id = request.getParameter("id");
		String pwd = request.getParameter("pwd");
		
		out.println("id="+id);
		out.println("pwd="+ShaManager.getHash(pwd));
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			try {
				con = DriverManager.getConnection("jdbc:mysql://localhost:3306/java","servlet","1234");

				if(con == null) {
					out.print("접속실패");
				}else {
					String sql = "select * from member where id=? and pwd =?";
					pstmt=con.prepareStatement(sql);
					pstmt.setString(1, id);
					pstmt.setString(2, ShaManager.getHash(pwd));
					rs = pstmt.executeQuery();
					StringBuffer tag= new StringBuffer();
					tag.append("<script>");
					if(rs.next()) {
						tag.append("alert('로그인성공');");
						tag.append("location.href = '/';");
						//로그인을 성공한 회원의 경우, 브라우저를 끄지 않는한 쿠키 값이 존재하기에
						// 계속해서 서버의 메모리에 회원정보를 저장할수 있는 객체를 올려야함.
						//이러한 객체를 session 객체라고 하며, 자동으로 고유 할당값이 배정되는데 
						//이를 가리켜 Session ID 라고 한다.
						//회원정보를 담아두고 , 유저가 브라우저를 닫기전 까지 보관해야한다.
						//(예외: 서버가 정한 시간동안 재요청이 없을경우 자동으로 세션소멸 시킴)
						
						// Tomcat이 관리하므로 개발자가 직접 new 할 수없는 인터페이스
						// 주의할 점 - 세션은 브라우저가 들어올 때 무조건 생성되는것이 아니라, 개발자가 아래의 세션을 건드리는 코드가 실행도리때
						//메모리에 올라옴? 아니다 -> 왜? 로그인을 의도하지 않은 브라우저의 요청 마저 세션을 만들필요가 없기 때문.
						HttpSession session =request.getSession();
						String sessionId = session.getId();//현재 생성된 세션에 자동으로 발급된 고유값
						//tag.append("세션의 ID는:"+sessionId);
						
						Member member = new Member();
						
						member.setMember_id(rs.getInt("member_id"));
						member.setId(rs.getString("id"));
						member.setPwd(rs.getString("pwd")); 
						member.setName(rs.getString("name"));
						member.setRegdate(rs.getString("regdate"));
						
						//회원 1명에 대한 정보가 채워진(DTO) 인스턴스를 세션에 담아두자
						//(브라우저를 종료할때 까지 회원 정보를 계속 보내줄수 있다.)
						//HttpSession 은 Map을 상속받음, 따라서 Map 형이다.
						//Map은 자바의 컬렉션 프레임 웤이다.(자료 구조) 
						//컬렉션프레임웤의 목적: 다수의 데이터 중 오직 객체만을 대상으로 효율적으로 데이터를 처리하기 위해 지원되는 자바의 라이브러리
						//java.util 패키지
						//1)순서 있는 객체(리스트),
						//2)순서 없는 객체를 다룰때(Set)
						//3)Key 와 Value의 쌍을 가지는 (Map)
						// js의 객체 표기법 자체가 Map으로 구성됨
						/*
						 * let member={
						 * 		name:"scott",
						 * 		age: 30
						 * }
						 * */
						session.setAttribute("member", member);
						
					}else {
						tag.append("alert('로그인 실패');");
						tag.append("history.back();");
					}
					tag.append("</script>");
					out.print(tag.toString());
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			if(rs!=null) {
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(con!=null) {
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
