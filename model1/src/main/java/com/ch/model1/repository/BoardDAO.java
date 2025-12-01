package com.ch.model1.repository;

import java.awt.Container;
import java.security.spec.DSAGenParameterSpec;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.ch.model1.dto.BoardDTO;
import com.ch.model1.util.PoolManager;

import javax.naming.InitialContext;
import javax.naming.NamingException;

//DB 의 CRUD 담당 DAO
public class BoardDAO {
	// Create(insert)
	// 글 1건을 등록하는 매서드
	PoolManager pool = new PoolManager();

	public int insert(BoardDTO boarddto) {// ******개발시 파라미터의 수가 많을경우 DTO를 만들어 사용한다. ********
		// 이 메서드 호출시 마다 접속을 일으키는것이 아니라 접속자가 없더라도
		// 톰켓이 미리 커넥션들을 확보해 놓은 커넥션 풀(Connection pool)로부터 대여해 보자.
		// 또한 쿼리문 수행이 완료되더라도 얻어온 커넥션은 절대로 닫지 않아야 한다.
		Connection con = null;
		PreparedStatement pstmt = null;
		int result = 0; // return 할 예정이므로
		try {
			con = pool.getConnection();
			// 쿼리 수행
			String sql = "Insert into board(title,writer,content) values(?,?,?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, boarddto.getTitle());
			pstmt.setString(2, boarddto.getWriter());
			pstmt.setString(3, boarddto.getContent());
			result = pstmt.executeUpdate();
			// DAO 에서는 출력문을 작성하는 순간 중립성에서 벗어나 버린다.
			// 따라서 리턴값을 줘서 다른 장소에서 확인하자.

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			// 주의 기존 JDBC 코드는 다 사용한 커넥션을 닫았지만, 풀로부터 얻어온 커넥션은 닫으면 안됨..
			if (con != null) {
				try {
					// 이 객체는 DataSource 구현체로부터 얻어온 Connection 이기 때문에 close() 메서드에 의해
					// 일반적 JDBC의 닫는 close()가 아님
					con.close();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return result;
	}

	// Read(select)
	public List selectAll() {
		// 커넥션 얻는 코드를 이 메서드에서 손수 하지 말자. PoolManager 가 대신해주니
		Connection con = pool.getConnection();// 풀매니저로부터 커넥션 객체를 얻어옴
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<BoardDTO> list = new ArrayList();
		try {
			String sql = "select board_id,title,writer,content,regdate,hit from board";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();// select 문의 반환값은 ResultSet 이다.
			//rs 는 무조건 닫아야 함으로, 외부의 jsp는 디자인을 담당하는 코드이지, ResultSet의 존재를 알 필요도 없고
			//또한 ResultSet DB연동기술이므로, 오직 DAO에서만 제어해야한다.
			//모순 : rs 를 닫아버린상태에서 외부객체에게 전달해주면 사용불가.
			//해결책 : rs 가 죽어도 상관없게 같은형식의 데이터를 담을 수 있는 List 에 담자.
			//			이 문제를 해결하기 위해 필요한 객체들의 조건!
			//			1)현실에 존재하는 사물을 표현할 수 있는 객체가 필요하다.(ex 게시물1건-DTO)
			//			2)DTO 로부터 생성된 게시물을 표현한 인스턴스 들을 모아놓을 객체가 필요함(순서,객체를 담을수 있어야함)
			//				ㄴ 이 조건에 만족하는 객체는 자바의 컬렉션 프레임웤중 List 이다.
			//collection framework 이란 : java.util에서 지원하는 라이브러리로서, 오직 객체만을 모아서 처리할때 유용한 api
			
			while(rs.next()) {
				
				BoardDTO boardDTO = new BoardDTO();//게시물 1건을 담을 수 있는 인스턴스
				boardDTO.setBoard_id(rs.getInt("board_id"));
				boardDTO.setTitle(rs.getString("Title"));
				boardDTO.setWriter(rs.getString("writer"));
				boardDTO.setRegdate(rs.getString("regdate"));
				boardDTO.setHit(rs.getInt("hit"));

				list.add(boardDTO);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			// 주의 기존 JDBC 코드는 다 사용한 커넥션을 닫았지만, 풀로부터 얻어온 커넥션은 닫으면 안됨..
			if (con != null) {
				try {
					// 이 객체는 DataSource 구현체로부터 얻어온 Connection 이기 때문에 close() 메서드에 의해
					// 일반적 JDBC의 닫는 close()가 아님
					con.close();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return list;
	}

	// Update

	// Delete
}
