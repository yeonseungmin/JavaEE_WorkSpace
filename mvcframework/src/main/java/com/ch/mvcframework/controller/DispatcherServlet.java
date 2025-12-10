package com.ch.mvcframework.controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*
 * 엔터 프라이즈급의 규모가 큰 애플리케이션에서 클라이언트의 수많은 요청마다
 * 1:1 대응하는 서블릿을 선언하고 매핑한다면,
 * 매핑규모가 너무나 방대해지고 유지보수성이 오히려 저해된다.
 * 
 * 해결책) 앞으로 요청에 대한 매핑은 오직 하나의 진입점으로 몰아서 관리하자.
 * (예 - 대기업의 고객센터와 흡사)
 * */
public class DispatcherServlet extends HttpServlet{
	
	//결국 if 문을 커멘드 패턴과 팩토리 패턴을 이용하여 대체하기 위한 준비물들...
	FileInputStream fis;
	Properties props;
	
	//아래의 init은 서블릿이 인스턴스가 생성되어진 직후에 호출되는 서블릿 초기화 목적의 메서드이다.
	// init() 메서드 안에 명시된 매개변수인 servlet config은 단어에서 이미 느껴지듯.. 
	// 이 서블릿과 관련된 환경 정보를 갖고 있는 객체이다.
	public void init(ServletConfig config) {
		try {
			fis = new FileInputStream("C:\\Workspace\\JavaEE_WorkSpace\\mvcframework\\src\\main\\webapp\\WEB-INF\\Servlet-Mapping.txt");
			props = new Properties();
			props.load(fis);// 프로퍼티스가 사용할 스트림 로드
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//음식, 영화, 블로그 ,음악 등등의 모든 요청을 이클래스에서 받아야 함,,
	//이때 요청시 메서드가 Get, Post Put, Delete.. 모든 종류의 요청을 다 받을 수 있어야한다.
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doRequest(request, response);
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doRequest(request, response);
	}
	
//	클라이언트의 요청방식이 다양함으로 어떤 요청방식(GET,POST)으로 들어 오더라도, 아래의 매서드 하나로 몰아넣으면 ,
//	코드는 메서드마다 재 작성할 필요가 없다.
	protected void doRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("클라이언트 요청 감지");
		/*
		 * 모든 컨트롤러의 5대 업무
		 * 1) 요청을 받는다.
		 * 2) 요청을 분석한다.
		 * 3) 알맞는 로직 객체에 전달한다.
		 * 4) 결과는 View 에서 보여줘야 함으로, View 페이지로 가져갈 결과 저장(requset	)
		 * 5) 결과 페이지 보여주기 
		 * 
		 * 
		 * */
		
		//요청 분석 : 음식, 영화 처리.. 현재 클라이언트가 요청한 유형이 무엇인지 파악
		//클라이언트가 요청 시 사용한 주소 표현식인 URI가 곧 클라이언트가 원하는게 무엇인지에 대한 구분값이기도 하다.
		
		String uri = request.getRequestURI();
		System.out.println("클라이언트가 요청시 사용한 uri는 ="+uri);
		
			//영화 전담 컨트롤러에게 요청 전달~~
//			MovieController controller = new MovieController();
//			controller.execute(request, response);

			//음식 전답 컨트롤러에게 요청 전달~~
//			FoodController controller = new FoodController();
//			controller.handle(request, response);

		/*
		 * 위의 if,else if문으로 클라이언트의 요청을 1:1로 처리하면 역시나 유지보수성이 떨어진다.
		 * 해결책) 각 요청을 조건문이 아닌 객체로 처리해야 함 = GOF(디자인 패턴 저자 들)는 Command Pattern + Factory Pattern 을 이용함
		 * Factory Pattern 이란?  객체의 생성방법에 대해서는 감춰놓고 개발자로 하여금 객체의 인스턴스를 얻어갈 수 있도록 정의하는 클래스 정의법
		 * */
		
		String controllerPath=props.getProperty(uri);
		System.out.println("영화에 동작할 하위 전문 컨트롤러는="+controllerPath);
		
		//여기까지는 하위 컨트롤러의 이름만을 추출한 상태이고 실제 동작하는 클래스 및 인스턴스는 아니다.
		try {
			//class : 클래스에 대한 정보를가진 예약어 클래스.. 현재 이 클래스가 보유한 메서드명, 생성자, 속성들
			Class clazz = Class.forName(controllerPath);//동적으로 로드된다.. (static == method 영역에)
			//static 영역에 올라온 클래스 원본 코드를 대상으로 인스턴스 1개 생성하기.. new연산자 만이 인스턴스를 만들수 있는 것은 아님..
			Object obj = clazz.getConstructor().newInstance();//deprecated.. 곧 사라질 명령어다.
			
			//메모리에 올라온 하위 컨트롤러 객체의 매서드 호출 
			//현재 시점에 메모리에 올라온 객체가 MovieController or FoodController인지 알수 없기 때문에,
			//이들의 최상위 객체인 Controller로 형변환 한다!!
			Controller controller = (Controller)obj;
			//아래의 메서드 호출의 경우, 분명 부모 형인 Controller형의 변수로 메서드를 호출하고 있으나.
			// 자바의 문법 규칙상 자식이 부모의 메서드를 오버라이드 한경우 자식의 메서드를 호출한다.
			// 즉 자료형은 부모형이지만, 동작은 자식 자료형으로 할 경우 현실의 생물의 다양성을 반영하였다해서 다형성(Polymorphism) 
			controller.execute(request,response);//메서드 호출 .. (다형성)
	
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
