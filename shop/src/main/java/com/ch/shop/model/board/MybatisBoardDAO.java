package com.ch.shop.model.board;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ch.shop.dto.Board;
import com.ch.shop.exception.BoardException;

import lombok.extern.slf4j.Slf4j;

/*
 * board 테이블에 대한 CRUD를 수행하되, 직접 쿼리문을 작성하지 않으며
 * mybatis 를 이용하되, 순수 mybatis가 아닌 Spring 을 mybatis 를 이용하자.
 * !!!!!!!! 컨트롤러 - 서비스 - DAO - mybatis !!!!!!!!!!!!!!
 * */
@Repository	//@Repository를 표시해 놓으면, 스프링이 자동 스캔에 의해 탐색한 후 인스턴스를 자동으로 생성해주고, 빈 컨테이너로 관리..
@Slf4j
public class MybatisBoardDAO implements BoardDAO{
	//스프링에서는 DI 를 적극 활용해야 하므로, 필요한 객체의 인스턴스를 직접 생성하면 안되고
	// 스프링 컨테이너로부터 주입(Injection) 받아야 한다.
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	
	public void insert(Board board) throws BoardException {
//		sqlSessionTemplate.insert("mybatis맵퍼",dto)
		try {
			sqlSessionTemplate.insert("Board.insert",board);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("게시물이 등록되지 않았습니다.");
			//throw 는 에러를 일으키는 메서드로 ( 외부에 알림 목적, 책임은 외부에 전가시킴)
			throw new BoardException("게시물 등록 실패",e);
			
		}
		
	}

	@Override
	public List selectAll() {
		return sqlSessionTemplate.selectList("Board.selectAll");
	}

	@Override
	public Board select(int board_id) {
		return sqlSessionTemplate.selectOne("Board.select",board_id);
	}

	@Override
	public void update(Board board) throws BoardException{	//DML 은 오류가능성 있음
		try {
			sqlSessionTemplate.update("Board.update",board);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new BoardException("수정실패",e); //일부러 오류 발생
		}
		
	}

	@Override
	public void delete(int board_id) throws BoardException{
		try {
			sqlSessionTemplate.delete("Board.delete",board_id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new BoardException("삭제실패",e);
		}
		
	}
	
	
	
}
