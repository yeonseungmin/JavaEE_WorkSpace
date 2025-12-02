<%@page import="com.ch.model1.dto.BoardDTO"%>
<%@page import="java.util.List"%>
<%@page import="java.sql.ResultSet"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@page import="com.ch.model1.repository.BoardDAO"%>
<%@ page import ="java.sql.ResultSet" %>
<%!
	//이 영역은 선언부 이기 때문에 이 jsp가 추후 서블릿으로 변경될때의 멤버영역
	BoardDAO boardDAO = new BoardDAO(); 
	
%>
<%
	// 이 영역은 스크립틀릿 이기 때문에 jsp 파일이 서블릿으로 변환될때 service() 메서드 영역
	// 얼마든지 DB연동이 가능하긴함..
	// But 디자인 코드와 데이터베이스 연동코드가 하나로 합쳐지면(스파게티 코드)가 되기에
	// 추후 DB 연동코드를 재사용하기 힘들다..
	List<BoardDTO> list = boardDAO.selectAll();
	out.print("컬랙션 수"+list.size());
	
	int totalRecord=list.size();
	int pageSize =10;//총 레코드 수만큼 출력하면 스크롤이 생기므로, 한페이지당 보여질 레코드 수 정함
	int totalPage=(int)Math.ceil((float)totalRecord/pageSize);//pageSize를 적용해버리면 26건일 경우, 나머지 16건은 볼 기회가 없기때문에,
																					//나머지 페이지 수를 보여줄 수 있도록 하자.
	int blockSize=10;//총 페이지 수 만큼 반복문을 돌리면 화면에 너무나 많은 페이지가 출력되므로 , 블락의 개념을 도입
	int currentPage =1;
	if(request.getParameter("currentPage")!=null){
		currentPage = Integer.parseInt(request.getParameter("currentPage"));
	}
	int firstPage = currentPage -(currentPage-1)%blockSize;
	int lastPage = firstPage+(blockSize-1);
	int curPos = (currentPage-1)*pageSize; // 페이지당 가져올 리스트의 시작 인덱스
	int num = totalRecord - curPos;
	
%>
<meta charset="UTF-8">
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<style>
table {
  border-collapse: collapse;
  border-spacing: 0;
  width: 100%;
  border: 1px solid #ddd;
}

th, td {
  text-align: left;
  padding: 16px;
}

tr:nth-child(even) {
  background-color: #f2f2f2;
}
</style>

</head>
<body>

<table>
  <tr>
    <th>No</th>
    <th>title</th>
    <th>writer</th>
    <th>regdate</th>
    <th>hit</th>
  </tr>
  <%
  	//rs에 들어있는 레코드들을 한칸씩 이동하며 꺼내자.
  	//rs.next() 가 true 인 경우
  		
  		for(int i=1; i<pageSize; i++){
  			if(num<1)break;//게시물 번호가 1보다 작아지면 더이상 데이터가 없으므로 종료 (List 에서 존재하지 않는 데이터 접근 방지)
  			BoardDTO boardDTO =list.get(curPos++);
  %>
  <tr>
    <td><%= num-- %></td>
    <td><a href="/board/detail.jsp?board_id=<%=boardDTO.getBoard_id()%>"><%= boardDTO.getTitle() %></a></td>
    <td><%= boardDTO.getWriter() %></td>
    <td><%= boardDTO.getRegdate() %></td>
    <td><%= boardDTO.getHit() %></td>
  </tr>
  <%} %>
  <tr>
  	<td ><button onClick="location.href='/board/write.jsp';">글 등록</button></td>
  	<td colspan="4" >
  		<%for (int i =firstPage; i<=lastPage; i++){ %>
  			<%if(i>totalPage)break; %>
  				<a href = "/board/list.jsp?currentPage=<%=i%>">[<%=i %>]</a>
  		<%} %>
  	</td>
  </tr>
</table>

</body>
</html>
