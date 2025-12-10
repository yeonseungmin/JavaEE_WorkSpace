package com.ch.mvcframework.controller;

import java.io.IOException;
 
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*
 * 일반
 * 추상 : 자식에게 추상메서드를 구현 강제할 수 있따는 장점은 있으나 
 * 				자식이 이미 누군가를 상속받았다면 다중상속의 문제가 발생
 * 인터페이스 : 클래스가 아닌 오직 추상메서드와 상수만을 보유할 수있는 대상이기 때문에
 * 					 자바의 다중 상속 문제를 피해갈 수 있다.
 * 					(클래스의 기능만을 인터페이스로 정의..?할 수있다?)
 * */
public interface Controller {
	//앞으로 이 인터페이스를 구현하는 모든 자식 객체가 반드시 아래의 매서드명을 구현한 것을 강제할수 있으므로
	// 메서드명을 통일할 수 있다는 장점이 있따.
	//또한 자식마다 구현 내용이 다르므로, 이 시점에 아래의 메서드의 내용을 채울 수도 없고 채워서도 안됨.
	public void execute(HttpServletRequest requset, HttpServletResponse response)throws ServletException,IOException; 
		
		
	
}
