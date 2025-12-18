package com.ch.shop.config.spring;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MyListener implements ServletContextListener{
	
	
	
	//애플리 케이션이 시작될때 호출되는 메서드
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		
		
		//Spring 프레임 웤에서는 이 시점에 ServletContext에게 AnnotationConfigApplicationContext 즉 스프링 컨테이너를 생성하며
		//비즈니스 로직이 들어있는 모델 영역과 관련된 빈들을 생성하여 관리하게 만들었을 것이다..
		ServletContext application=sce.getServletContext();
		
		String contextClass =application.getInitParameter("contextClass");
		log.info("애플리케이션 시작"+contextClass);
		
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		//context 자바의 설정파일을 읽어들여, 빈들의 인스턴스를 생성하고, 관리했을것..
		application.setAttribute("contextContext", context);
		
		
	}
	
	
	//애플리 케이션이 종료될때 호출되는 메서드
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		log.info("애플리케이션 종료");
	}
	
}
