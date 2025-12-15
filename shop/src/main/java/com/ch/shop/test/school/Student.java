package com.ch.shop.test.school;

public class Student {
	
	private Bell bell;
	
	public Student(Bell bell) {
		this.bell=bell;
	}
	
	public void gotoSchool() {
//		System.out.println("딩동댕");	//공통 코드
		bell.ding();
		System.out.println("학교가야줴~~");
		System.out.println("가지말까.....");
	}
	public void study() {
//		System.out.println("딩동댕");
		bell.ding();
		System.out.println("공부해라~~");
		System.out.println("나도 잘 하지 못했짜나~..");
	}
	public void rest() {
//		System.out.println("딩동댕");
		bell.ding();
		System.out.println("쉬어봐라~~");
		System.out.println("끼얏호~~");
	}
	public void havLunch() {
//		System.out.println("딩동댕");
		bell.ding();
		System.out.println("밥먹어라~~");
		System.out.println("끼얏호~!~!~!");
	}
	public void gotoHome() {
//		System.out.println("딩동댕");
		bell.ding();
		System.out.println("집에가라~~");
		System.out.println("끼얏호~!~!~!~!~!");
	}
	
	
}
