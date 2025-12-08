<%@page import="com.ch.mybatisapp.dto.News"%>
<%@page import="org.apache.ibatis.session.SqlSession"%>
<%@page import="com.ch.mybatisapp.mybatis.MybatisConfig"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%!
	
%>
<%
	MybatisConfig mybatisConfig = MybatisConfig.getInstance();

	SqlSession sqlSession = mybatisConfig.getSqlSession();
	
	//Mybatis 는 개발자가 자바 소스안에 쿼리문을 작성하도록 하지 않음..
	//즉 개발자는 쿼리문을 xml에 작성해야함!! --> 잡다한 코드 제거 해줌
	/* sqlSession.insert("쿼리문을 작성한 xml의 아이디",파라미터); */
	News news = new News();
	
	news.setTitle("마이바티스 연습용");
	news.setWriter("떵훈");
	news.setContent("나 집에 갈래~");
	
	int result = sqlSession.insert("com.ch.mybatisapp.dto.News.insert",news);
	
	//mybatis의 SqlSession은 DML 수행시 트랜젝션을 반드시 commit 해야함
	sqlSession.commit();
	
	if(result <1){
		out.print("등록실패");
	}else{
		out.print("등록성공");
	}
	mybatisConfig.release(sqlSession);
%>