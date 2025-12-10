package com.ch.mvcframework.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ch.mvcframework.repository.BoardDAO;

public class ListController implements Controller	{
	BoardDAO boardDAO = new BoardDAO();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//3단계 : 알맞는 로직 객체에 일 시킨다.
		List list=boardDAO.selectAll();
		request.setAttribute("list", list);
		
		
	}

	//현재 컨트롤러에서는 디자인 관련한 응담을 해서도 안되고,클라이언트에게 특정페이지로 재접속 하라는 응답정보 조차 보내면 안됨
	//즉, 클라이언트와의 응답 정보에 대한 처리는 전면부에 나선 DispatcherServlet이 담당하기 때문에
	//그러면 하위 컨트롤러에서는 무엇을 담당하는가? DispatcherServlet 이 보여줘야 할 뷰페이지에 대한 정보만을 반환하면 됨
	// 또한, 뷰 페이지에 대한 정보 반환시 왜 .jsp 파일을 직접 명시하지 않는가.(하드코딩)
	//전통적으로 유지보수성을 높이기 위해서는 자바 클래스 내의 자원의 주소등은 하드코딩 하지 않는 습관이 중요!!
	@Override
	public String getViewName() {
		
		return "/board/list/result";
	}

	@Override
	public boolean isForward() {
		// jsp 까지 살려서 데려갈 데이터가 있다면 포워딩 해야한다. true !!
		return true;
	}
	
}
