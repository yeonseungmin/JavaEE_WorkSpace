package com.ch.mvcframework.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ch.mvcframework.dto.Board;
import com.ch.mvcframework.repository.BoardDAO;

//글 쓰기 요청을 처리하는 하위 컨트롤러
/*
 * 5대 업무
 * 1) 요청을 받는다 (DispatcherServlet)
 * 2) 요청을 분석해서 전달한다. (DispatcherServlet)
 * 		3) 알맞는 로직 객체에게 일 시킨다. (컨트롤러 - > 하위 컨트롤러)
 * 		4) 결과 페이지에 가져갈 것이 있을 경우 결과를 저장(session X , request O)
 * 5) 컨트롤러는 디자인에 관여하면 안되므로, 알맞는 View 페이지를 보여주기
 * */
public class RegistController implements Controller{	//3,4) 하위 컨트롤러

	BoardDAO boardDAO = new BoardDAO();
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			
		//3단계) 로직 객체에게 일시키기!!
		String title=request.getParameter("title");
		String writer=request.getParameter("writer");
		String content=request.getParameter("content");
		
		Board board = new Board();
		board.setTitle(title);
		board.setWriter(writer);
		board.setContent(content);
		
		int result = boardDAO.insert(board);
		//등록 후 성공 시, 게시물 목록을 보여줘야 함..
		//아래의 코드는 직접 클라이언트에게 보여주는 역할을 수행하고 있으므로 위 클래스에서는 월권 행위 이다.(하드코딩)
//		response.sendRedirect("/board/list.jsp");
		
		
		
	}
	//DispatcherServlet 이 보여줘야할 페이지 정보를 반환
	public String getViewName() {
		return "/board/regist/result";
	}
	@Override
	public boolean isForward() {
		// TODO Auto-generated method stub
		return false;//포워딩이 아닌 브라우저로 하여금 재접속 하라는 의미
	}
	
}
