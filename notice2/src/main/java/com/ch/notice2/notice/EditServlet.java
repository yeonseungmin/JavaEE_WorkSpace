package com.ch.notice2.notice;

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

public class EditServlet extends HttpServlet {
	Connection con; // 접속 수행 객체
	PreparedStatement pstmt; // 쿼리문 수행하는 객체

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter(); // 톰켓이 html 작성시 사용할 내용을 담을 출력 스트림
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/java", "servlet", "1234");
			if (con == null) {
				out.println("연결실패");
			} else {
				
				String title = request.getParameter("title");
				String writer = request.getParameter("writer");
				String content = request.getParameter("content");
				int notice_id = Integer.parseInt(request.getParameter("notice_id"));

				String sql = "update notice set title=?,writer=?,content=? where notice_id = ?";
				pstmt = con.prepareStatement(sql);

				pstmt.setString(1, title);
				pstmt.setString(2, writer);
				pstmt.setString(3, content);
				pstmt.setInt(4, notice_id);

				int result = pstmt.executeUpdate();// DML 반영
				out.print("<script>");
				if (result < 1) {
					out.print("alert('수정 실패');");
					out.print("history.back();");
				} else {
					out.print("alert('수정 성공');");
					// 주의 ) detail.jsp는 반드시 notice_id값을 필요로 하므로 ,링크 사용시 /notice/detail.jsp 는 오류가 발생
					out.print("location.href ='/notice/detail.jsp?notice_id=" + notice_id + "';");
				}
				out.print("</script>");

				out.println("title" + title);
				out.println("writer" + writer);
				out.println("content" + content);
				out.println("notice_id" + notice_id);
			}
			// DML 중 수정 SQL - update notice set title=파라미터,writer=파라미터,content=파라미터
			// where notice_id = 파라미터;
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
