<%@ page contentType="text/html; charset=UTF-8"%>
<meta charset="UTF-8">
<%
	//하나의 페이지에 많은 양의 데이터가 출력되면, 스크롤이 발생함으로 한페이지당
	// 보여질 레코드의 수에 제한을 걸고 나머지 데이터에 대해서는 여러 페이지 링크를 지원해주려면,
	//총 게시물 수에 대해 산수 계산이 요구된다.
	
	//기본 전제 조건 - 총 레코드 수가 잇어야 한다.
	int totalRecord =2600;//총 레코드 수 (가정)
	int pageSize = 10;//한페이지당 보여질 레코드 수
	int totalpage=(totalRecord%pageSize==0)? totalRecord/pageSize:totalRecord/pageSize+1;
	int blockSize = 10;// 블럭당 보여질 페이지 수
	int currentPage = 1;// 현재 유저가 보고있는 페이지
	if(request.getParameter("currentPage")!=null){
	currentPage =Integer.parseInt(request.getParameter("currentPage"));		
	}
	int firstPage = currentPage-(currentPage-1)%blockSize;//블럭당 반복문의 시작 값
	int lastPage = firstPage+(blockSize-1);// 블럭당 반복문의 끝 값
	int num = totalRecord - pageSize*(currentPage-1); //페이지당 시작 No.번호 뒤로 갈수록 차감
%>
<%= "totalRecord"+totalRecord+"<br>" %>
<%= "pageSize"+pageSize+"<br>" %>
<%= "totalpage"+totalpage+"<br>" %>
<%= "현재 당신이 보고있는 페이지는 :"+currentPage+"<br>" %>
<%= "firstPage :"+firstPage+"<br>" %>
<%= "num :"+num+"<br>" %>
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
a{text-decoration:none;}

/*유저가 현재 보고있는 페이지 표시*/
.numStyle{
	font-size:30px;
	font-weigth:bold;
	color:red;
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

<%for(int i=1;i<=pageSize; i++){ %>
  <tr>
  <%if(num<1)break; %>
    <td><%=num-- %></td>
    <td>참깨 스틱</td>
    <td>쫀맛탱</td>
    <td>쿠쿠루</td>
    <td>삥뽕</td>
  </tr>
  <%} %>
  <tr>
  	<td colspan ="5" align="center">
  		<a href="/paging/list.jsp?currentPage=<%=firstPage-1 %>">◁</a>
  		<%for(int i=firstPage; i<=lastPage; i++){ %>
  		<%if(i>totalpage)break; %>
  		
  		
  			<a <%if(currentPage == i){ %>class="numStyle"<%} %> href ="/paging/list.jsp?currentPage=<%=i%>">[<%= i %>]</a>
  			
  			<%} %>
  		<a href="/paging/list.jsp?currentPage=<%=lastPage+1 %>">▷</a>
  		
  	</td>
  </tr>
</table>

</body>
</html>