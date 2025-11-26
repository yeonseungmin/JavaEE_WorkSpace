package com.ch.notice2.notice;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ResponseCache;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*글 한건 삭제 요청 처리하는 Servlet */
/*
  delete from notice where notice_id="넘겨받을 인자";
  primary key 값은 내용이 같지 않으며, 보안상 중요하지 않기 때문에 get방식을 이용
 */
public class DeleteServlet extends HttpServlet {
	
	
	String url = "jdbc:mysql://localhost:3306/java";
	String user = "servlet";
	String password = "1234";
	Connection con; // 접속 수행 객체
	PreparedStatement pstmt; // 쿼리문 수행하는 객체

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		//jsp에서의 page 지시영역과 동일한 효과를 주기위한 코드
		response.setContentType("text/html;charset=utf-8");	//MIME 타입(브라우저가 이해하는 형식을 작성해야함!!!)
																//image/jpg,	application/json,
		
		// 클라이언트가 요청을 시도하면서 함께 지참해온 notice_id 파라미터 값을 출력받자
		String notice_id = request.getParameter("notice_id");
		System.out.println("넘겨받은 아이디는:" + notice_id);
		PrintWriter out = response.getWriter();	//톰켓이 저장해놓는 변수
		// 드라이버 로드
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			// 접속
			con = DriverManager.getConnection(url, user, password);
			if (con == null) {
				out.print("드라이버 접속 실패");
			} else {
				
				// 쿼리문 날리기 delete from notice where notice_id=넘겨받은 파라미터
				String sql = "delete from notice where notice_id=" + notice_id;
				pstmt = con.prepareStatement(sql);
				int result = pstmt.executeUpdate();
				
				out.print("<script>");
				if(result <1) {
					out.print("alert('삭제 실패');");
					out.print("history.back();");
				}else {
					out.print("alert('삭제 성공');");
					out.print("location.href='/notice/list.jsp';");
				}
				out.print("</script>");
			}
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// 자원 해제
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (con != null) {
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
