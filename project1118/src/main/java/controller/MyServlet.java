package controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sound.sampled.AudioFormat.Encoding;

import org.apache.tomcat.util.buf.Utf8Decoder;

/*
 * 클래스 중 오직 javaEE의 서버에서만 해석 및 실행되어질 수 있는 클래스를 가리켜 
 * Servlet이라 한다.
 * 현재 클래스를 Servlet으로 만들려면 HttpServlet을 상속 받으면 된다.
 * */
public class MyServlet extends HttpServlet{
								// ㄴ(ClassName) is a (HttpServlet) 같은 자료형으로 본다. = 상속관계
	@Override
	public void init(ServletConfig config) throws ServletException {
		// 이 서블릿이 컨테이너에 의해 최초로 인스턴스가 생성될때 초기화를 위해 무조건 호출되는 메서드
		// 주의!!) 생성자도 아니며 그냥 일반 메서드인데 , 단지 생성자 호출 직후 초기화를 위해 이른 시점에 호출되는것 뿐
		// 			서블릿의 생성주기 3가지중 첫번째 매서드( init(), service() , destory() )
		// 서블릿의 생성은 컨테이너( 고양이 서버 ) 가 담당하며, 이 서블릿의 초기화 정보를 넘겨줌
		 System.out.println("나 방금태어나서 초기화 되었어 init()");
		super.init(config);
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//현재 클래스를 웹브라우저로 요청하는 클라이언트에게 메세지 출력
		PrintWriter out=resp.getWriter();//문자기반의 출력스트림 얻기
		//개발자가 이 출력 스트림에 문자열을 저장해두면, 고양이 서버가 알아서 웹브라우저에 출력해버림
		out.print("abcd");
	}
	//서블릿 인스턴스가 소멸될때, 호출되는 매서드(called by tomcat)
	public void destory() {
		

	}
}
