package com.ch.mvcframework.dto.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/*
 * 이 필터의 목적은 DispatcherServlet 이 요청을 처리하기 전에
 * 공통적으로 처리할 업무 중 파라미터에 대한 인코딩 처리를 미리하기 위함..
 * 여기서 처리해 놓으면 하위 컨트롤러에서 일일이 인코딩 처리를 할 필요가 없어지기 때문에
 * 마치 계곡 물의 상류에서 무언갈 처리하는것과 같다.
 * */
public class CharacterEncodingFilter implements Filter {
	String encoding;
	public void init(FilterConfig filterConfig) {
		encoding = filterConfig.getInitParameter("encoding");
		System.out.println("내가 원하는 인코딩은?"+encoding);
	}
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		request.setCharacterEncoding(encoding);
		//개발자가 필터를 재정의 하면서 필터 체인의 흐름을 다시 원활하게 처리하지 않으면
		//요청은 현재 필터에서 멈춘상태에 빠진다.
		//따라서 요청 흐름을 정상화 해주자
		chain.doFilter(request, response);// 요청처리가 가던 흐름을 그대로 갈 수 있또록..
		
	}

}
