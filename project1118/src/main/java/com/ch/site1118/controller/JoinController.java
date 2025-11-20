package com.ch.site1118.controller;

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
//클라이언트가 전송한 파라미터들을 받아서 ,오라클에 넣기
//클라이언트의 요청이 웹브라우저 임으로 즉 웹상에 요청을 받을 수 있고, 오직 서버에서만 실행 될 수 있는
// ㄴ클래스인 서브릿으로 정의하자

import com.ch.site1118.util.EmailManager;
public class JoinController extends HttpServlet{
	EmailManager emailManager = new EmailManager(); // null
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
		PreparedStatement pstmt =null;
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
				//쿼리 수행 PreparedStatement 인터페이스가 담당
				//JDBC는 데이터베이스 제품의 종류가 무엇이든 상관없이 DB를 제어할 수 있는 
				// ㄴ 코드가 동일함.. (일관성 유지 가능)
				// 가능한 이유? 사실 JDBC 드라이버를 제작하는 주체는 벤더사이기 때문에..
				// 모든 벤더사는 java 언어를 제작한 오라클 사에서 제시한 JDBC 기준 스펙을 따르기 때문에 가능하다..
				//참고로 우리가 javaEE 시간에 별도의 개발툴킷을 설치할 필요가 없었던 이유?
				// 오라클 사는 javaEE 에 대한 스팩만을 명시하고, 실제 서버는 개발하지 않는다 .
				//결국 javaEE 스펙을 따라 서버를 개발하는 벤더사를 모두가 각자 고유의 기술로
				// 서버는 개발하지만, 반드시 javaEE 에서 명시된 객체명을 즉,api명을 유지해야하므로 
				// java개발자들은 어떠한 종류의 서버이던 상관없이 그 코드가 유지됨.
				String sql ="insert into member(member_id,id,pwd,name,email) ";
				sql +=" values(seq_member.nextval,?,?,?,?)";// ? 바인드 변수
				
				request.setCharacterEncoding("utf-8");
				String id = request.getParameter("id");
				String password = request.getParameter("pwd");
				String name = request.getParameter("name");
				String email = request.getParameter("email");
				
				// 바인드 변수를 사용하게되면, 물음표 값이 무엇인지 반드시 명시해야함
				pstmt=con.prepareStatement(sql);
				pstmt.setString(1,id);
				pstmt.setString(2,password);
				pstmt.setString(3,name);
				pstmt.setString(4,email);
				
				//쿼리문 실행
				int result = pstmt.executeUpdate(); //DML 수행시 사용하는 메서드
				//executeUpdate() 실행후 반환값이 int, 그리고 이 int 의 의미는 현재 쿼리문에 의해 영향을 받은
				//레코드의 수를 반환 예) insert 후 1건이 반환되므로 1이 반환,update,delete는 n 을 반환
				// 0이면 쿼리 반영 실패.
				if(result != 0) {
					out.print("가입성공");
					emailManager.send(email);//받는사람주소
				}else {
					out.print("가입실패");
				}
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
			if(pstmt != null) {
				try {
					pstmt.close();
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}				
			}
		}
		
		
		
		

		
	}
}
