<%@page import="com.ch.mvcframework.movie.model.MovieManager"%>
<%@ page contentType="text/html; charset=UTF-8" %>

<%
	// 클라이언트가 전송한 파라미터를 받아 영화에 대한 피드백 메시지 만들기
	request.setCharacterEncoding("utf-8");		// 파라미터에 대한 한글 인코딩 
	String movie = request.getParameter("movie");	
	out.print(movie);
 	// 각 영화에 대한 메시지 만들기	//javaSE에서 사용가능한 코드임으로 코드 분리
	String msg = "선택한 영화가 없음";
	if(movie!=null){
		if(movie.equals("귀멸의 칼날")){
			msg="최신 일본 애니메이션 개봉작";
		}else if(movie.equals("공각기동대")){
			msg="SF";
		}else if(movie.equals("에이리언")){
			msg="외계 생명체 SF 시리즈";
		}else if(movie.equals("소울")){
			msg="pixar 애니메이션";
		}
	} 

	
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script>
	function request(){
		document.querySelector("form").action = "/movie/script/form.jsp";
		document.querySelector("form").method = "post";
		document.querySelector("form").submit();
	}
	
	addEventListener("load", ()=>{
		document.querySelector("button").addEventListener("click", ()=>{
			request();	
		});
	});
</script>
</head>
<body>
	<form>
		<select name="movie">
			<option value="귀멸의 칼날">귀멸의 칼날</option>
			<option value="공각기동대">공각기동대</option>
			<option value="에이리언">에이리언</option>
			<option value="소울">소울</option>
		</select>
		<button type="button">피드백 요청</button>
	</form>
	<!-- 
			이 프로그램에 대해 유지보수성을 고려할 필요가 없을 정도로 간단한 기능으로 판단된다면
			굳이 유지보수성을 염두해 둔 java 클래스까지 도입할 필요가 없다.
			따라서 스크립트만으로 해결해보자 - 이러한 개발 방식을 가리켜 이름조차 없는 막개발 방식이라 함... 스크립트 위주의 개발방식
			아주 간단한 분야에 유용함
	 -->
	<h3>
		선택한 결과 <br>
		<span style="color:red">
			<%=msg%>
		</span>
	</h3>
	
</body>
</html>