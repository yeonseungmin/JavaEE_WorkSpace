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

// 회원 등록 요청을 처리할 서블릿 클래스
// HTTP 요청 방식 중, 클라이언트가 서버로 데이터를 전송해 오는 방식은 POST 방식임
// 따라서 HTTP 서블릿이 보유한 doXX형 메서드 중 doPost를 재정의 해야함
public class RegistController extends HttpServlet{
	//Post 요청을 처리하는 매서드
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("클라이언트의 post 요청 감지"); // 웹 브라우저가 아닌 현재 콘솔에 찍힘
		//클라이언트가 전송한 id, pwd, name 을 받아서 출력해 보기
		
		request.setCharacterEncoding("utf-8");
		
		String id = request.getParameter("id");
		String pwd = request.getParameter("pwd");
		String name = request.getParameter("name");
		
		System.out.println("전송받은 id는" + id);
		System.out.println("전송받은 pwd는" + pwd);
		System.out.println("전송받은 name는" + name);
		
		//응답 객체가 보유한 문자기반의 출력 스트림에 , 개발자가 유저에게 전달하고 싶은 메세지를 보관하자
		response.setContentType("text/html"); //브라우저에게 이 문서의 형식이 html 임을 알린다.
		response.setCharacterEncoding("utf-8");//이 html 에서 사용할 문자열에 대해 전세계 모든 언어가
																//깨지지 않도록 , UTF-8
		PrintWriter out= response.getWriter();
		
		// mysql 에 넣어주기
		
		// java 언어가 해당 데이터베이스 서버를 제어하려면 ..접속에 앞서 최우선으로 해당 DB제품을 핸들링 할 수 있는 라이브러리인
		// 라이브러리 = 드라이버 를 보유하고 있어야 한다.(jar형태)
		// 보통 드라이버는 java 가 자체적으로 보유할 수 없다.(java 입장에서는 어떤 DB가 있는지 알수 없기에)
		// 따라서 드라이버 제작의 의무는 db제품을 판매,공유할 벤더사에게 있다.
		
		//jvm의 3가지 메모리 영역 중 Method 영역에 동적으로 클래스를 Load 시킴
		// 보통은 jvm 자동으로 로드해주지만, 개발자가 원하는 시점에 원하는 클래스를 로드시킬 경우 아래와 같은
		//Class 클래스가 static 메서드인 forName()메서드를 사용 하기도 한다.
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("드라이브 로드 성공");
		} catch (ClassNotFoundException e) {
			System.out.println("드라이브 로드 실패");

			e.printStackTrace();
		}
		
		// mysql 에 접속하기
		// 자바에서 데이터베이스를 다루는 기술을 가리켜 JDBC (Java DataBase Connectivity)
		//이 기술을 javaSE 의 java.sql 패키지에서 주로 지원함
		// 현재 우리가 개발중인 분야가 javaEE 라면 javaSE 를 이미 포함하고 있다.
		String url = "jdbc:mysql://localhost:3306/java";
		String user ="servlet";
		String pass = "1234";
		
		Connection con = null;	//둘다 finally 에서 보이도록
		PreparedStatement pstmt=null;
		try {
			 con =DriverManager.getConnection(url,user,pass);
			// Connection 이란 ? 접속 성공 후 그 정보를 가진 객체이므로, 접속을 끝ㅎ고 싶은 경우 이 객체를 이용하면됨
			// 예) con.close(); 접속해제
			// 주의 !! jdbc에서 데이터베이스에 접속 성공 여부를 판단할 경우 절대로 catch문이 실패라고 생각하면 안됨 !
			// getConnection() 메서드가 반환하는 Connection 인터페이스가 null인지 여부로 판단
			
			if(con==null) {
				System.out.println("접속 실패");
			}else {
				System.out.println("접속 성공");
				//insert 문 
				//JDBC 객체중 쿼리 수행을 담당하는 객체가 바로 PreparedStatement 인터페이스다.
				// 그리고 이 객체는 접송을 성공해야 , 얻을수 있다.
				
				pstmt =con.prepareStatement("insert into member(id,pw,name) values('"+id+"','"+pwd+"','"+name+"')");
				
				// 준비된 쿼리 문을 실행하자.
				int result = pstmt.executeUpdate();//DML, 메서드 실행후 반환되는 값은 이 메서드에 의해 영향을 받은 레코드 수가
												// 반환됨.. 따라서 1보다 작은수가 반환되면 이 쿼리에 의해 영향을 받은 레코드가 없으므로
												// 수행실패
				if(result <1) {
					System.out.println("등록실패");
					out.print("<script>");
					out.print("alert('등록실패');");
					out.print("</script>");
				}else {
					System.out.println("등록 성공");
					//웹 브라우저에 성공 메세지 출력하기
					out.print("<script>");
					out.print("alert('등록성공');");
					out.print("</script>");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}				
			}
			if(con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
		}
		
	}
	
}
