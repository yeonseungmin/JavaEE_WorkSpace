package com.ch.gallery.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//데이터베이스에 등록된 갤러리 목록을 출력하기 위한 서블릿
public class ListServlet extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("/text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		//출력 스트림에 클라이언트에게 보여줄 태그를 넣어두자
		//서블릿은 서버에서 실행되는 javaEE 기반의 클래스로서, 서블릿없이는 javaEE 개발 자체가 불가능하다.
		//하지만, 디자인 페이지 작성시 너무 비효율적이다.
		//따라서 servlet과 동일한 목적의 기술인 PHP,ASP.net등에 비해 경쟁력이 떨어진다.
		// 해결책 ) Servlet을 보완한 서버측 스크립트 기술로 JSP 지원 
//		<!DOCTYPE html>
//		<html>
//		<head>
//		<meta name="viewport" content="width=device-width, initial-scale=1">
//		<style>
//		table {
//		  border-collapse: collapse;
//		  border-spacing: 0;
//		  width: 100%;
//		  border: 1px solid #ddd;
//		}
//
//		th, td {
//		  text-align: left;
//		  padding: 16px;
//		}
//
//		tr:nth-child(even) {
//		  background-color: #f2f2f2;
//		}
//		</style>
//		</head>
//		<body>
//
//		<h2>Zebra Striped Table</h2>
//		<p>For zebra-striped tables, use the nth-child() selector and add a background-color to all even (or odd) table rows:</p>
//
//		<table>
//		  <tr>
//		    <th>First Name</th>
//		    <th>Last Name</th>
//		    <th>Points</th>
//		  </tr>
//		  <tr>
//		    <td>Jill</td>
//		    <td>Smith</td>
//		    <td>50</td>
//		  </tr>
//		  <tr>
//		    <td>Eve</td>
//		    <td>Jackson</td>
//		    <td>94</td>
//		  </tr>
//		  <tr>
//		    <td>Adam</td>
//		    <td>Johnson</td>
//		    <td>67</td>
//		  </tr>
//		</table>
//
//		</body>
//		</html>

		
	}
}
