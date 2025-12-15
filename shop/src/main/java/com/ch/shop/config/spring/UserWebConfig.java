package com.ch.shop.config.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.ch.shop.controller.shop.BoardController;

/*
 * !!! 이 클래스는 로직을 작성하기 위함이 아니라 애플리케이션에서 사용할 빈(객체)들 및 그들간의 관계(weaving)까지
 *  명시하기 위한 목적의 클래스이며, 쇼핑몰의 일반 유저들이 보게 되는 애플리케이션 쪽 빈(@Bean) 들을 관리한다
 * 
 * 
 * */
@Configuration //단지 xml 을 대신한 설정용 클래스에 불과해!!
@EnableWebMvc 	//!!!!!!!! 필 수 설 정 !!!!!!!! 스플링이 지원하는 MVC 프레임 워크를 사용하기 위한 어노테이션)
// @ComponentScan = 일일이 빈으로 등록할 필요가 없는 많이 알려진 빈들을 가리켜 
//스프링에서는 컴포넌트라고 한다. 또한 이 컴포넌트들은 패키치 위치만 설정해 놓으면
//스프링이 알아서 찾아내서(검색) 인스턴스를 자동으로 만들어준다.
//MVC 에서의 Controller는 @Controller 를 붙임
//MVC 에서의 DAO 는 @Repository 를 붙임
//MVC 에서의 DAO 는 @Service 를 붙임
//MVC 에서의 특정 분류가 딱히 없음에도 자동으로 올리고 싶다면 @Component
@ComponentScan(basePackages = "com.ch.shop.controller" )
public class UserWebConfig {

//	컨트롤러 페이지에 @Controller 지정하면 자동으로 올려줌 !!!!!!!!!
//	@Bean	//으로 하면 컨트롤러 하나당 1:1 로 올려야 함
//	public BoardController boardController() {
//		
//		return null;
//	}
	
	
}
