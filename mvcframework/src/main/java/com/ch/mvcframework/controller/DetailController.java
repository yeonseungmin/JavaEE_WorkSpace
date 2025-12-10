package com.ch.mvcframework.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ch.mvcframework.dto.Board;
import com.ch.mvcframework.repository.BoardDAO;

//게시물 1건 요청을 처리하는 하위 컨트롤러
public class DetailController implements Controller{
	BoardDAO boardDAO = new BoardDAO();
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//3단계 업무) 알맞는 로직객체에 일시키기
		String board_id = request.getParameter("board_id");
		Board board = boardDAO.select(Integer.parseInt(board_id));
		System.out.println(board);
		
		//board를 결과 페이지인 (MVC중 view) 까지 살려서 가져가려면 포워딩 해야한다.
		//request 객체에 담고
		request.setAttribute("board", board);
		
	}

	@Override
	public String getViewName() {
		// 위에서 경로를 직접 적으면 안됨
		return "/board/detail/result";
	}

	@Override
	public boolean isForward() {
		// TODO Auto-generated method stub
		return true;
	}

}
