package com.ch.model1.news;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ch.model1.dto.News;
import com.ch.model1.repository.NewsDAO;

public class RegistServlet extends HttpServlet{
	//뉴스기사를 등록하기위한 서블릿
	NewsDAO newsDAO = new NewsDAO();
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//클라이언트가 동기방식을 ㅗ전송한 파라미터를 받아서 데이터 베이스(DAO이용하여 간접적으로 일을 시키자) 넣자
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		
		String title = request.getParameter("title");
		String writer = request.getParameter("writer");
		String content = request.getParameter("content");
		
		PrintWriter out = response.getWriter();
		
		News news = new News();
		news.setTitle(title);
		news.setWriter(writer);
		news.setContent(content);

		int result = newsDAO.insert(news);

		//클라이언트가 동기 방식의 요청을 했기 때문에 서버는 화면전환을 염두해 두고,
		//순수 데이터보다는 페이지 전환 처리가 요구됨..
		//글등록 후 , 클라이언트의 브라우저로 하여금 다시 목록 페이지를 재요청하도록 만들자!!
		//response.sendRedirect("");// 이 코드 대신 location.href 를 사용해도 동일 효과
		StringBuffer sb = new StringBuffer();
		sb.append("<script>");
		if(result<1) {
			sb.append("alert('등록실패')");
			sb.append("history.back();");
		}else {
			sb.append("alert('등록성공');");
			sb.append("location.href='/news/list.jsp';");
		}
		
		sb.append("</script>");
		out.print(sb.toString());
		
	}
}
