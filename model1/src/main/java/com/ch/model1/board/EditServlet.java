package com.ch.model1.board;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ch.model1.dto.BoardDTO;
import com.ch.model1.repository.BoardDAO;
import com.google.protobuf.DescriptorProtos.SourceCodeInfo.Location;

//수정 요청을 처리하는 서블릿
public class EditServlet extends HttpServlet{
	BoardDAO boardDAO = new BoardDAO();
	BoardDTO boardDTO = new BoardDTO();
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/* 파라미터 4개를 넘겨받아 DAO에게 시키자.*/
		response.setContentType("text/html;charset=utf-8");	// 응답 페이지에 대한 인코딩
		request.setCharacterEncoding("utf-8"); //파라미터 값에 대한 인코딩
		PrintWriter out = response.getWriter();
		
		String board_id = request.getParameter("board_id");
		String title = request.getParameter("title");
		String writer = request.getParameter("writer");
		String content = request.getParameter("content");
		
		boardDTO.setTitle(title);
		boardDTO.setWriter(writer);
		boardDTO.setContent(content);
		boardDTO.setBoard_id(Integer.parseInt(board_id));
		
		int result = boardDAO.update(boardDTO);
		out.print("<script>");
		if(result <1) {
			out.print("alert('수정실패');");
			out.print("history.back()");
		}else {
			out.print("alert('수정성공');");
			out.print("location.href='/board/detail.jsp?board_id="+board_id+"';");
			
		}
		out.print("</script>");
		
	}
}
