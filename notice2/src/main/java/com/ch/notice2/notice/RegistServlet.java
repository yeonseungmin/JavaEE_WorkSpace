package com.ch.notice2.notice;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.Identity;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.function.IntBinaryOperator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mysql.cj.x.protobuf.MysqlxCrud.Insert;

public class RegistServlet  extends HttpServlet{
	//Html로 부터 글쓰기 요청을 받는 서블릿 정의
	//jsp는 사실 서블릿 이므로 현재 서블릿의 역할을 대신할 수도 있다.
	//하지만, jsp 자체가 서블릿의 디자인 능력을 보완하기 위해 나온 기술이므로,
	// 현재 서블릿에는 servlet 이 좀더 적합하다.
	String url ="jdbc:mysql://localhost:3306/java";
	String user = "servlet";
	String pwd ="1234";
	Connection con;	//접속 수행 객체
	PreparedStatement pstmt;	//쿼리문 수행하는 객체
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//클라이언트가 전송한 파라미터를 받자
		System.out.println("요청감지");
		response.setContentType("text/html");	//톰캣이 전송할때 한글
		response.setCharacterEncoding("utf-8");	//톰캣이 전송할때 한글
		request.setCharacterEncoding("utf-8");	//클라이언트가 전송할때 한글
		
		String title = request.getParameter("title");//전송파라미터 명
		String writer = request.getParameter("writer");
		String content = request.getParameter("content");
		
		PrintWriter out=response.getWriter();
		out.print("클라이언트가 전송한 제목은" + title +"<br>");
		out.print("클라이언트가 전송한 작성자는" + writer +"<br>");
		out.print("클라이언트가 전송한 내용은" + content +"<br>");
		
		//mysql의 java db 안에 notice 에 insert !!!
		//필요한 라이브러리가 있을경우 maven 빌드 툴에서 해결하자.
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			out.print("드라이버 로드성공");
			con=DriverManager.getConnection(url,user,pwd);
			if(con ==null) {
				out.println("con접속실패");
			}else {
				out.println("con접속 성공");
				
				//쿼리문 수행
				String sql = "Insert into notice(title, writer, content) values(?,?,?)";
				pstmt=con.prepareStatement(sql); //pstmt 인스턴스 얻기
				pstmt.setString(1, title);
				pstmt.setString(2, writer);
				pstmt.setString(3, content);
				
				//insert 실행 (DML 일 경우 executeUpdate() 호출, 반환값은 레코드 수가 반환 insert는 1을 반환)
				int result = pstmt.executeUpdate();
				if(result <1) {
					out.println("insert실패");
				}else {
					out.println("insert성공");
					// 지정한 브라우저로 다시 재접속 
					response.sendRedirect("/notice/list.jsp");
				}
			}
		} catch (ClassNotFoundException e) {
			out.print("드라이버 로드 실패");
		}
			catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
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
