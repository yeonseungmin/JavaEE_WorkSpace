package com.ch.site1118.controller;

import java.io.IOException;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// 회원 등록 요청을 처리할 서블릿 클래스
// HTTP 요청 방식 중, 클라이언트가 서버로 데이터를 전송해 오는 방식은 POST 방식임
// 따라서 HTTP 서블릿이 보유한 doXX형 메서드 중 doPost를 재정의 해야함
public class RegistController extends HttpServlet{
	//Post 요청을 처리하는 매서드
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("클라이언트의 post 요청 감지"); // 웹 브라우저가 아닌 현재 콘솔에 찍힘
		//클라이언트가 전송한 id, pwd, name 을 받아서 출력해 보기
		
		request.setCharacterEncoding("utf-8");
		
		String id = request.getParameter("id");
		String pwd = request.getParameter("pwd");
		String name = request.getParameter("name");
		
		System.out.println("전송받은 id는" + id);
		System.out.println("전송받은 pwd는" + pwd);
		System.out.println("전송받은 name는" + name);
		// mysql 에 넣어주기
		
		
	}
	
}
