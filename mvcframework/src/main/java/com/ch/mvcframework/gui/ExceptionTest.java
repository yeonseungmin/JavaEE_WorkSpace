package com.ch.mvcframework.gui;

public class ExceptionTest {

	public static void main(String[] args) {
		
		/*
		 * 프로그램의 비정상 수행을 방해하는 요인을 오류라고 한다.
		 * 1)에러 - 네트워크가 끊기거나,램이 타버렸다.. 프로그램 외적인 상황에 의한 오류
		 * 2)예외 - 프로그래밍 적으로 해결할 수 있는 오류
		 * 		자바에서 예외란?
		 * 		1)강요된 예외 - 개발자가 try-catch 즉 예외처리 하지 않으면 컴파일 조차 허용 하지 않는 예외
		 * 		2)런타임 예외	- 개발자가 예외처리를 하지 않아도 가용하지 않는 예외.. 컴파일은 가능하나 중간에 비정상 종료 가능성 높음
		 * 
		 * 
		 * 
		 * */
		int[] arr = new int[3];
		arr[0] =1;
		arr[1] =2;
		arr[2] =3;
		arr[3] =4; // 런타임 에러
	}
}
