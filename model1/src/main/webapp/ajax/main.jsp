<%@page import="com.ch.model1.repository.Member2DAO"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import ="com.ch.model1.dto.Member2DTO"%>
<%@ page import = "java.util.List" %>

<%!
	Member2DAO dao =new Member2DAO();
%>
<%
	List<Member2DTO> memberList = dao.selectAll();
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
    .container{
        width: 650px;
        height: 500px;
        background-color: aliceblue;
        margin: auto;
    }
    .aside{
        width: 150px;
        height: 100%;
        background-color: antiquewhite;
        float: left;
    }
    .content{
        width: 500px;  
        height: 100%;
        background-color: rgb(201, 163, 106);
        float: left;
    }
    .aside input{
        width: 90%;
        
    }
    .aside button{
        width: 45%;
        height: 10%;
    }
</style>
<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script>
// 문서가 로드되면 두개의 버튼에 대해 이벤트 연결
// 화살표 함수 - 기존 함수 정의를 줄이려고 function(){} -> ()=>{}
	
	//비동기 방식 함수 작성 (크롬과 같은 웹브라우저가 대신 서버와의 통신을 담당하고,
	//			그 시간 동안 자바스크림트가 원래 하고자 했던 로직을 그대로 수행)
	//추후, 서버로 부터 응답이 오면, 크롬 브라우저는 자바 스크립트에게 보고를 하게되며,
	//이때 서버로부터 가져온 html 이 아닌 순수한 데이터를 자바스크립트에게 전달한다.
	//그러면 이 데이터를 자바스크립트를 순수 데이터를 이용하여 화면에 동적으로 출력한다.
	function sendASync(){
		//비동기 방식의 핵심이 되는 자바 스크립트 객체가 바로 XMLHttpRequest 이다.
		let xhttp = new XMLHttpRequest();// 주의 ! 이객체가 서버로 요청을 떠나는것이 아니라
														// 크롬브라우저가 요청을 시도하러 가는 것..
		
		//크롬등의 브라우저가 서버로부터 응답을 받을때 발생하는 이벤트를 처리하는 속성
		//브라우저가 서버로부터 응답을 받으면 onload 에 지정한 콜백 함수를 자동으로 호출(호출 주체: js)
		xhttp.onload = function(){
			//alert("비동기");
			//서버가 보내온 데이터를 담고 있는 속성인 responseText를 이용해보자.
			//console.log("서버로부터 받은 목록 정보는?"+xhttp.responseText);
			//서버로부터 전송되어온 문자열을 대상으로 원하는 값 추출하기
			let memberList=JSON.parse(xhttp.responseText); // 문자열을 해석하여 JSON 구분 형식에 맞을경우 객체 리터럴로 전환해줌
			// 정말로 obj 가 자바스크립트의 인스턴스라면, 객체.속성을 접근할 수 있따.
			//따라서 검증해보자.
			//console.log("email은 ", obj.email);
			console.log("서버가 보내온",xhttp.responseText);
			console.log("서버가 보낸 문자열을 파싱한 결과(객체화)",memberList);
			//서버가 보내온 데이터가 다행이도 JSON 표기법을 준수했으므로 , 지금부터 객체화 시켜 화면에 렌더링해보자.
			
			
		}
		
		//요청할 준비
		xhttp.open("POST","/ajax/async_regist.jsp"); // 어떤 서버의 주소에 요청을 시도하고, 어떤 Http메서드로 요청을 시도할지 결정하는 메서드
		
		//Http메서드가 post인경우 헤더값을 다음과 같이 설정해야 한다.(평소엔 웹브라우저가 대신해줌)
		//반드시 open 메서드가 먼저 나와야 한다.
		xhttp.setRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=utf-8");
		
														
		//브라우저에게 요청
		xhttp.send("id="+$("input[name='id']").val()+"&name="+$("input[name='name']").val()+"&email="+$("input[name='email']").val());//요청 시작
		
		
	}

    $(()=>{
    	// 동기버튼에 클릭 이벤트 연결
    	//  	장점 - 순서에 의해 실행되므로, 이전단계의 실행이 완료되어야 나중 순서 로직이 실행되기 때문에
   		// 				순서가 엉키지 않아 안정적
   		//		단점 - 만일 앞선 실행부가 반복문이나, 대기상태에 빠질경우, 후순위 로직은 실행이 지연
        $($("form button")[0]).click(()=>{
            $("form").attr({
                action:"/ajax/regist.jsp",
                method:"post"
            }); 
            $("form").submit();
        });

        // 비동기버튼에 클릭 이벤트 연결
        /*비동기 - 순서를 지키지 않는 방식
        	장점 - 순서를 지키지 않기 때문에, 앞선 실행부가 대기 상태에 빠지더라도 후순위 실행이 영향을 받지않느다.
        	단점 - 서버로부터 응답 받는 데이터 형식이 html 이 아니므로 새로고침 현상은 발생하지 않으나
        			페이지 디자인을 동적으로 처리하는데 많은 시간과 노력이 필요함(렌더링)
        		참고) 페이지를 동적으로 처리하는 양이 너무 가혹하여 페이스 북 개발자들이 만들어낸
        				JavaScript 기반의 프레임 워크가 바로 React 이다. Vue.js
        */
        $($("form button")[1]).click(()=>{
        	sendASync();
        });
    });
</script>
</head>
<body>
	<div class =container>
		<div class = aside>
            <form >
			<input type="text" placeholder="ID" name="id">
            <input type="text" placeholder="Name" name="name">
            <input type="text" placeholder="Email" name="email">
            <button>동기 등록</button>
            <button type ="button">비동기등록</button>
            </form>
		</div>
		<div class = content>
			<table width ="100%" border = "1px">
				<thead>
					<th>ID</th>
					<th>Name</th>
					<th>Email</th>
				</thead>
				<tbody>
				<%for(int i=0; i<memberList.size(); i++){ %>
				<%Member2DTO dto = memberList.get(i); %>
					<tr>
						<td><%=dto.getId() %></td>
						<td><%=dto.getName() %></td>
						<td><%=dto.getEmail() %></td>
					</tr>
					<%}%>
				</tbody>
			</table>
		</div>
		
	</div>

</body>
</html>