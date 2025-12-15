package com.ch.shop.config.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.ch.shop.test.food.Cook;
import com.ch.shop.test.food.FriPan;
import com.ch.shop.test.food.Induction;
import com.ch.shop.test.food.Pan;
import com.ch.shop.test.school.Bell;
import com.ch.shop.test.school.Student;

/*
 * 스프링에서 전통적으로 DI를 구현하기 위해 개발자가 필요로 하는 자바의 클래스 (빈 = bean)들을
 * xml에서 정의해 왔다.. BUT
 * 최근의 개발방법은 자바 클래스를 이용하는 방법을 추천하고 있다.
 * 따라서 아래의 클래스는 로직을 작성하기 위함이 아니라, 오직 개발자가 사용하고 싶은 클래스들의 명단을 작성하기 위함이다.
 * 그리고 이렇게 등록된 클래스 각각을 가리켜 javaEE 분야에서는 특히 bean 이라 부른다.
 * 
 * 또한 아래의 클래스 안에 @Bean을 등록해 놓으면 스프링 프레임웤이 자동으로 인스턴스화 시켜 메모리에 모아놓는데,
 * 이때 이 역활을 수행하는 스프링의 객체를 가리켜 ApplicationContext라 하며, 일명 스프링 컨테이너라 부르기도 함
 * 
 * 
 * */

@Configuration // 아래의 클래스는 로직용이 아닌 설정용 클래스임을 선언
public class AppConfig {
	/* 애플리케이션에서 사용할 모든 객체들을 등록하자 */
	@Bean	//bean을 명시해줘야 한다.
	public FriPan friPan(){
		return new FriPan();
	};

	@Bean
	public Induction induction() {
		return new Induction();
	}
	
	//아래와 같이 Bean 들간의 관계를 표현해 놓은것을 weaving 한다고 한다.
	@Bean
	public Cook cook(Induction pan) {
		return new Cook(pan);
	}
	
	/*--------------------------------------------------------------------------*/
	@Bean
	public Bell bell() {
		return new Bell();
	}
	@Bean
	public Student student(Bell bell) {
		return new Student(bell);
	}
	
}
