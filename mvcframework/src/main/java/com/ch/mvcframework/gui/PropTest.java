package com.ch.mvcframework.gui;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class PropTest {

	public static void main(String[] args) {
		//Map을 상속받은 객체
		//아래의 객체는 단독적으로 파일을 접근할 능력은 없음.. 따라서 java.io의 스트림 객체들을 이용해야한다.
		/*
		 * java.io 의 스트림 
		 * 		1) 방향에 따른 기준 - 입력 : 실행중인 프로그램에서 데이터가 들어오는 모습 입력 
		 * 								출력 : 실행중인 프로그램에서 데이터가 흘러나가는 모습 출력 
		 * 2) 데이터 처리방법 - 바이트 스트림 
		 * 							문자 스트림 
		 * 							버퍼 스트림
		 */						
		Properties props = new Properties();
		try {
			FileInputStream fis = new FileInputStream("C:\\Workspace\\JavaEE_WorkSpace\\mvcframework\\src\\main\\webapp\\WEB-INF\\Servlet-Mapping.txt");
			props.load(fis);// 이 시점부터 프로퍼티스 객체는 파일의 내용을 로드 한 상태
			String value =props.getProperty("/movie.do");
			System.out.println(value);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

}
