package com.ch.mvcframework.repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.ch.mvcframework.dto.Board;
import com.ch.mvcframework.mybatis.MybatisConfig;

// Model
public class BoardDAO {
	MybatisConfig mybatisConfig=MybatisConfig.getInstance();
	
	//글 1건 등록
	public int insert(Board board) {
		int result =0;
		SqlSession sqlSession =mybatisConfig.getSqlSession();
		result = sqlSession.insert("Board.insert",board);
		//SqlSession 은 디폴트로 autocommit 속성이 false로 되어있음
		//즉 commit하지 않으면 insert가 db에 확정되지 않음
		//commit() 은 DML을 대상으로만 한다.
		sqlSession.commit();
		mybatisConfig.release(sqlSession);
		return result;
	}
	public List selectAll() {
		List list = null;
		SqlSession sqlSession = mybatisConfig.getSqlSession();
		list = sqlSession.selectList("Board.selectAll");
		mybatisConfig.release(sqlSession);
		
		return list;
	}
	public Board select(int board_id) {
		Board board = null;
		SqlSession sqlSession = mybatisConfig.getSqlSession();
		board= sqlSession.selectOne("Board.select", board_id);
		mybatisConfig.release(sqlSession);
		return board;
	}
	
//	1건 삭제
	public int delete(int board_id){
		int result =0;
		SqlSession sqlSession = mybatisConfig.getSqlSession();
		result = sqlSession.delete("Board.delete",board_id);
		sqlSession.commit();
		mybatisConfig.release(sqlSession);
		
		return result;
	}
	
	//1건 수정
	public int update(Board board) {
		int result=0;
		SqlSession sqlSession = mybatisConfig.getSqlSession();
		sqlSession.update("Board.update", board);
		sqlSession.commit();
		mybatisConfig.release(sqlSession);
		return result;
		
	}
	
	
	
}
