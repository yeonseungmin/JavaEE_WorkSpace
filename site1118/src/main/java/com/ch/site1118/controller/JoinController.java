package com.ch.site1118.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//클라이언트가 전송한 파라미터들을 받아서 ,오라클에 넣기
//클라이언트의 요청이 웹브라우저 임으로 즉 웹상에 요청을 받을 수 있고, 오직 서버에서만 실행 될 수 있는
// ㄴ클래스인 서브릿으로 정의하자
public class JoinController extends HttpServlet{
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//나의 이름이 웹 브라우저에 출력되기 끔 ..
		//web.xml 에서 서블릿 매핑

		
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		
		PrintWriter out= response.getWriter();// 응답 객체가 보유한 출력 스트림 얻기
		
		out.print("<h1>동훈바보</h1>");//브라우저에 곧바로 데이터가 전송되는게 아니라, 
													// 톰캣과 같은 컨테이너 서버가 out.print()에 누적되어있는 문자열을 대상으로
													//새로운 html 문서를 작성할때 사용
		//JDBC 를 오라클에 insert
		// 드라이버가 있어야 오라클을 제어할 수 있다. 따라서 드라이버 jar 파일을 클래스 패스에 등록하자.
		// 하지만 , 현재 사용중인 IDE 가 이클립스라면 , 굳이 환경변수 까지 등록할 필요가 없고,
		//이클립스에 등록하면 된다.
		Connection con = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			out.print("드라이버 로드 성공");
			//오라클 접속
			String url = "jdbc:oracle:thin:@localhost:1521:XE";
			String user ="servlet";
			String pwd ="1234";
			
			//접속이 성공했는지 알기 위해서는 Connection 인터페이스가 null 인지 판단.
			con = DriverManager.getConnection(url,user,pwd);
			
			if(con == null) {
				out.print("\n 서버 접속 실패");
			}else {
				out.print("\n 서버 접속 성공");
			}
		} catch (ClassNotFoundException e) {
			out.print("드라이버 로드 실패");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			if(con != null) {
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
