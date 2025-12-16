package com.ch.shop.model.board;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import com.ch.shop.dto.Board;
import com.ch.shop.exception.BoardException;

/*
 * 서비스는 모델 영역에서 여러 모델 객체들에게 일을 시키는 역할을 수행
 * 대표적 업무) 여러 DAO 들에게 일을 시키고, 트랜잭션 상황에서 트랜잭션을 처리할 의무를 가진 객체	!!!!! 중요 !!!!!
 * 만일 서비스의 존재가 없을 경우, (컨트롤러가 서비스의 역할을 수행하게 되므로), 이때부터는 컨트롤러의 코드에
 * 모델영역의 업무가 혼재되어, MVC의 원칙이 깨져버린다.. 추후 코드의 분리가 될 수 없다.
 * 즉 모델 코드를 재사용 할 수 없게 된다.
 * 
 * !!!!!!!! 컨트롤러 - 서비스 - DAO - mybatis !!!!!!!!!!!!!!
 * */

@Service		//ComponentScan의 대상이 되어 자동으로 인스턴스를 올리고, 빈컨테이너에서 관리해달라는 뜻
public class BoardServiceImpl implements BoardService{	// is a 관계
	
	@Autowired
	private BoardDAO boardDAO;

	//DAO에 글 등록 일 시키기
	// DAO에 글 등록 시킬때 발생하는 에러를 전달해야 함으로 throws 로 책임 전가
	public void regist(Board board) throws BoardException{
		boardDAO.insert(board);
	}

	@Override
	public List selectAll() {
		return boardDAO.selectAll();
	}

	@Override
	public Board select(int board_id) {
		return boardDAO.select(board_id);
	}

	@Override
	public void update(Board board) throws BoardException{
		boardDAO.update(board);
		
	}

	@Override
	public void delete(int board_id) throws BoardException{
		boardDAO.delete(board_id);
		
	}
	
}

