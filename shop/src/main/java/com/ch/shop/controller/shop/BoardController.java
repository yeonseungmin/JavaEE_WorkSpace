package com.ch.shop.controller.shop;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/*
 * 우리가 제작한 MVC 프레임 웤에서는 모든 요청마다 1:1 대응하는 컨트롤러를 매핑하는 방식이었으나..
 * 스프링 MVC는 예를 들어 게시판 1개에 대한 목록, 쓰기, 상세보기, 수정, 삭제에 대해 하나의 컨트롤러로 처리가 가능함!!!
 * 왜? 모든요청마다 1:1 대응하는 클래스 기반이 아니라 .. 매서드 기반이기 때문에 !!!!
 * */

@Controller
public class BoardController {

	//글 목록 페이지 요청 처리
	@RequestMapping("/board/list")	//HandlerMapping 종류중 인기
	public ModelAndView getList() {
		System.out.println("클라이언트에 목록 요청 감지");
		return null;
	}
	
	// 글 쓰기 요청처리
	
	
	// 글 상세 보기 요청처리
	
	
	//글 수정 요청 처리
	
	
	//글 삭제 요청 처리
	
}
