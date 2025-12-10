package com.ch.mvcframework.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ch.mvcframework.food.model.FoodManager;

/*음식에 대한 판단 요청을 처리하는 컨트롤러
MVC - 개발이론, 방법론
Model2 - 그 이론을 바탕으로 javaEE기술로 구현해놓은 모델
			M - java 순수 클래스
			V - JSP,HTML
			C -1) 웹서버에서 실행될 수 있어야 한다
				2) 클라이언트의 요청을 받을 수 있어야 한다. = servlet
					ㄴ 결론 = Servlet 밖에 없음
					
 Model2 중 컨트롤러의 요건*/
public class FoodController implements Controller{
	FoodManager fdmanager = new FoodManager();

	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//클라이언트의 요청 파라미터 받기
		request.setCharacterEncoding("utf-8");
		String food = request.getParameter("food");
		
		//모델 객체에 일 시키기 
		String msg = fdmanager.getAdvice(food);//모델 객체에 일 시키기!!
		
		request.setAttribute("msg", msg);// 요청에 대한 응답이 완료되기 전까지는 서버에서 살아있음
		RequestDispatcher dis = request.getRequestDispatcher("/food/result.jsp");
		dis.forward(request,response);
		
	}

	@Override
	public String getViewName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isForward() {
		// TODO Auto-generated method stub
		return false;
	}
	
	
}
