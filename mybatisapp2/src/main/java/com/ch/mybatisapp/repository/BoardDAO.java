package com.ch.mybatisapp.repository;

import org.apache.ibatis.session.SqlSession;

import com.ch.mybatisapp.dto.Board;
import com.ch.mybatisapp.mybatis.MybatisConfig;

public class BoardDAO {

//	SqlSessionFactory 가 들어있는 싱글톤 객체
	MybatisConfig mybatisConfig = MybatisConfig.getInstance();
	
	public int insert(Board board) {
		int result =0;
		//상투적 JDBC code 사용하지 말자!!, Mybatis에게 맡기자.
		SqlSession sqlSession = mybatisConfig.getSqlSession();
		result = sqlSession.insert("com.ch.mybatisapp.dto.Board.insert", board);
		
		//DML 일 경우 트랜잭션을 확정지어야 한다.
		sqlSession.commit();
		mybatisConfig.release(sqlSession);
		
		return result;
	}
	
}
