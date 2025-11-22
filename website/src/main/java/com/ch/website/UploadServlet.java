package com.ch.website;

import java.io.File;
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

import com.ch.website.util.StringUtil;
import com.oreilly.servlet.MultipartRequest;

public class UploadServlet extends HttpServlet{
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String user = "servlet";
	String pwd = "1234";
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html;charset=utf-8");
		PrintWriter out= resp.getWriter();
		
		int maxSize = 1024*1024;
		MultipartRequest multi = new MultipartRequest(req, "C:\\Upload2",5*maxSize,"utf-8");
		String title = multi.getParameter("title");
		out.println("클라이언트가 전송한 제목은"+ title);
		out.print("<br>");
		
		long time = System.currentTimeMillis();
		out.print(time);
		out.print("<br>");
		
		String oriName = multi.getOriginalFileName("photo");
		out.print("<br>");
		String extend = StringUtil.getExtendFrom(oriName);
		out.print("<br>");
		
		File file = multi.getFile("photo");
		out.print("<br>");
		out.print(file);
		
		String filename = time +"."+ extend;
		String cusname = multi.getParameter("cusname");	// **************수정
		boolean result = file.renameTo(new File("C:\\Upload2/" + filename));
		if(result == true) {
			Connection con =null;
			PreparedStatement pstmt = null;
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
				con = DriverManager.getConnection(url,user,pwd);
				if(con == null) {
					out.print("오라클 드라이버 접속 실패");
					out.print("<br>");
				}else {
					out.print("오라클 드라이버 접속 성공");
					out.print("<br>");
					String sql ="insert into website(website_id,title,filename,cusname) values(seq_website.nextval,?,?,?)";
					pstmt = con.prepareStatement(sql);
					pstmt.setString(1, title);
					pstmt.setString(2, filename);
					pstmt.setString(3, cusname); //******cusname 자리
					
					int n = pstmt.executeUpdate();
					if(n<1) {
						out.print("sql쿼리 삽입 실패");
					}else {
						resp.sendRedirect("/upload/List2.jsp");
					}
				}
			} catch (ClassNotFoundException e) {
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				if(pstmt != null) {
					try {
						pstmt.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}if(con != null) {
					try {
						con.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			
		}else {
			out.print("file.renameTO 실패");
		}
	}
}
