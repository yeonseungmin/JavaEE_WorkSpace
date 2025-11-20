package com.ch.site1118.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*
 * 오라클에 들어있는 회원의 목록을 가져와서 화면에 출력
 */
public class MemberList extends HttpServlet{
	//클라이언트인 브라우저가 목록을 달라고 요청할 것이기 때문에, doXXX형 메서드 중
	// doGet() 을 재정의 하자
	//클라이언트가 목록을 원하기 때문에..
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//드라이버 로드
		response.setContentType("text/html;charset=utf-8");
//		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			out.print("드라이버로드 성공");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
