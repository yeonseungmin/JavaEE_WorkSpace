package com.ch.model1.board;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ch.model1.repository.BoardDAO;

public class DeleteServlet extends HttpServlet{
	//delete from board where board_id=?;
	BoardDAO boardDAO = new BoardDAO();
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		//파라미터 받기(파라미터 값에 한글이 포함되어있지 않기에 인코딩(utf-8)은 굳이 필요하지 않다.
		String board_id = request.getParameter("board_id");
		
		//DAO 에게 일시키고 삭제를 처리..
		int result = boardDAO.delete(Integer.parseInt(board_id));
		
		StringBuffer tag = new StringBuffer();
		tag.append("<script>");
		if(result<1) {
			tag.append("alert('삭제실패');");
			tag.append("history.back();");
		}else {
			tag.append("alert('삭제성공');");
			tag.append("location.href='/board/list.jsp';");
		}
		tag.append("</script>");
		
		out.print(tag.toString());
	}
}
