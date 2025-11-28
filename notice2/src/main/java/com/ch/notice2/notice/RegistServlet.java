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

import com.ch.notice2.domain.NoticeDTO;
import com.ch.notice2.repository.NoticeDAO;
import com.mysql.cj.x.protobuf.MysqlxCrud.Insert;

public class RegistServlet  extends HttpServlet{
	
	// 다른 로직은 포함되어 있지 않고 , 오직 DB와 관련 CRUD 만을 담당하는 중립적 객체를 사용하자.
	NoticeDAO noticeDAO = new NoticeDAO();
	//Html로 부터 글쓰기 요청을 받는 서블릿 정의
	//jsp는 사실 서블릿 이므로 현재 서블릿의 역할을 대신할 수도 있다.
	//하지만, jsp 자체가 서블릿의 디자인 능력을 보완하기 위해 나온 기술이므로,
	// 현재 서블릿에는 servlet 이 좀더 적합하다.
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
		NoticeDTO noticeDTO = new NoticeDTO();
		noticeDTO.setTitle(title);
		noticeDTO.setWriter(writer);
		noticeDTO.setContent(content);
		//성공실패 여부
		int result = noticeDAO.regist(noticeDTO);
		out.print("<script>");
		if(result <1) {
			out.print("alert('등록실패');");
			out.print("history.back();");
		}else {
			out.print("alert('등록성공');");
			out.print("location.href='/notice/list.jsp';");
		}
		out.print("</script>");
	}
}
