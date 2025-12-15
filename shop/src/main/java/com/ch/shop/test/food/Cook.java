package com.ch.shop.test.food;

/* 현실의 요리사를 정의 */
public class Cook {
	private Pan pan;//  has a  관계
	
	/*
		new 연산자 뒤에 정확한 자료형의 "생성자"가 오기 때문에 has a  관계를 상위 자료형으로 느슨하게 처리해도
	의존성이 높게됨.. 해결책 > 굳이 현재 클래스에서 직접 인스턴스를 생성하려고 하지말고, 
	외부의 어떤 주제가 대신 인스턴스를  생성하여, 메서드를 주입을 시켜주면 됨
	스프링에서는 이 외부의 주체가 바로 스프링의 애플리케이션 컨텍스트라는 객체를 담당하게 됨
	 
	 */
	public Cook(Pan pan) { 	
//		pan = new Induction();
		
	}
	
	//특정 객체를 필요로 할때는 그 객체의 상위 자료형을 매개변수로 갖는 setter나 또는 생성자를 준비하면 
	public void setPan(Pan pan) {
		this.pan = pan;
	}
	
	public void makeFood() {
		pan.boil();
	}
}
