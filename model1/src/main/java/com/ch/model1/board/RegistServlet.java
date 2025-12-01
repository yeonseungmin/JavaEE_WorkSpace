package com.ch.model1.board;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ch.model1.dto.BoardDTO;
import com.ch.model1.repository.BoardDAO;

// 글 등록 요청을 처리하는 servlet
public class RegistServlet extends HttpServlet{
	//RegistServlet has a BoardDAO
	//자바의 객체와 객체 사이의 관계를 명시할 수 잇는데 단, 2가지 유형으로 나뉜다.
	//자바에서 특정 객체가 , 다른 객체를 보유한 관계를 has a 관계라고 한다.
	//자바에서 특정 객체가 , 다른 객체를 상속받는 관계를 is a 라고 한다.
	BoardDAO boardDAO = new BoardDAO();// 서블릿의 생명주기 에서 인스턴스는 최초의 요청에 의해 1번만 생성되므로,
																// 서블릿의 멤버변수로 선언한 객체로 따라서 1 번 생성
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//jsp 의 page 지시영역과 동일한 코드
		response.setContentType("text/html; charset=UTF-8");
		request.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		//넘겨받은 파라미터를 이용하여 DB 전담 객체에게 시킴(재사용성 높임)
		String title = request.getParameter("title");
		String writer = request.getParameter("writer");
		String content = request.getParameter("content");
		
		//파라미터를 DB에 넣기
		//insert 메서드를 호출하기 전에 낱개로 존재하는 파라미터 들을 DTO 에 모아서 주자.
		BoardDTO boardDTO = new BoardDTO();
		boardDTO.setTitle(title);
		boardDTO.setWriter(writer);
		boardDTO.setContent(content);
		
		int result = boardDAO.insert(boardDTO);
		
		out.print("<script>");
		if(result < 1) {
			out.print("alert('등록실패');");
			out.print("history.back();");
		}else {
			out.print("alert('등록성공');");
			out.print("location.href='/board/list.jsp';");
		}
		out.print("</script>");
	}
}
