package com.ch.model1.singleton;
/*
 * 전세계 개발자들의 공통적 코드 패턴마다 이름을 붙여서 저서를 한 책의 이름
 * Design Pattern 이다.
 * 이 책이 출간된 이후부터, 개발 시 패턴의 이름을 제시하면 개발자 간의 업무 소통이 원할해짐
 * 
 * Singleton : 하나의 클래스로부터 오직 1개의 인스턴스 생성만 허용하는 클래스 정의 기법
 * */
public class Dog {
	private static Dog instance;
	//클래스는 사용하기 위해서 정의했으므로,
	//생성자를 private 지정한 후 아무것도 보완하지
	//않으면 , 절대로 Dog는 외부에서 사용할 수 없다.
	private Dog() {
		
	}
	//외부의 객체가 접근할 수 있는 일반 메서드 제공(생성자를 막았으므로)
	//아래의 매서드는 static 수식자(modifer)가 붙지 않았기 때문에
	//인스턴스 소속 메서드가 된다.. 즉 외부에서 이 메서드를 호출하려면
	//new Dog ()으로 강아지의 인스턴스를 생성한후 , 그 인스턴스를 통해서 접근가능
	//따라서 static 으로 선언하여 new 생성자를 사용하지 않고 사용
	public static Dog getInstance() {
		if(instance == null) {			
			instance = new Dog();
		}
		return instance;
	}
	
	public void bark() {
		System.out.println("크르릉 동훈! 동훈!");
	}
	
}
