package com.ch.site1118.controller;

import java.io.IOException;
import java.io.PrintWriter;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//javaEE 기술이 서블릿 기반이므로, 디자인 결과물까지 out.print()문자열로 처리해야함 ..
// 따라서 웹페이지의 양이 많아지거나, 디자인 코드량이 많아지면 유지보수성이 현저히 떨어짐
//즉 디자인 표현에 취약함


public class ListController extends HttpServlet{
		protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException{
			response.setContentType("text/html");//클라이언트에게 응답할 데이터의 유형을 명시
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			
			//String immutable 불변이므로 , 수정이 안된다. 따라서 수정가능한 문자열 처리를 위한 객체인
			//StringBuffer(동기화 지원X), String Builder(동기화 지원 O)을 사용하자!!!!!!
			StringBuffer sb = new StringBuffer();
			
			sb.append( "<table border = \"1px\">");
			for(int i =5; i>0; i--) {
				sb.append("<tr>")	;
				for(int j=3; j>0; j--) {
					sb.append(	"<td>"+i+"0"+j+"호</td>");					
				}
				sb.append("</tr>");				
			}
			sb.append("</table>");
		
			out.print(sb.toString());
			
		
	}
}
