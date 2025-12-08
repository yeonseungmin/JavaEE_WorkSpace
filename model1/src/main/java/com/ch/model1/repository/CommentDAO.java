package com.ch.model1.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.tomcat.jdbc.pool.PooledConnection;

import com.ch.model1.dto.Comment;
import com.ch.model1.dto.News;
import com.ch.model1.util.PoolManager;

//오직 comment 테이블에 대한 CRUD 만을 수행하는 DAO
public class CommentDAO {
	PoolManager pool = PoolManager.getInstance();
	//댓글 등록
	public int insert(Comment comment) {
		int result = 0;
		
		String sql ="insert into comment(msg, reader, news_id) values(?,?,?)";
		Connection con =pool.getConnection();
		PreparedStatement pstmt =null;
		try {
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, comment.getMsg() );
			pstmt.setString(2, comment.getReader());
			pstmt.setInt(3, comment.getNews().getNews_id());//객체 지향 이므로 , 부모를 int 형이 아닌 객체형태로 has a 로 보유
			
			result = pstmt.executeUpdate();
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			pool.freeConnection(con, pstmt);
		}
		
		
		return result;
	}
	
	
	//특정 뉴스 기사에 딸려있는 댓글 모두 가져오기
	public List<Comment> selectByNewsId(int news_id) {
		PoolManager pool = PoolManager.getInstance();
		Connection con =pool.getConnection();
		PreparedStatement pstmt=null;
		ResultSet rs =null;
		List<Comment> list = new ArrayList(); //Comment DTO를 모아놓을 리스트(자바에서는 배열보다는 컬렉션을 선호한다.)
		
		String sql = "select * from comment where news_id=?";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, news_id);
			rs = pstmt.executeQuery();// 쿼리 실행후 레코드 담기
			while(rs.next()) {// 레코드 수 만큼
				Comment comment = new Comment();
				comment.setComment_id(rs.getInt("comment_id"));
				comment.setMsg(rs.getString("msg"));
				comment.setReader(rs.getString("reader"));
				comment.setWritedate(rs.getString("writedate"));
				
				//부모인 뉴스의 정보도 담기!!
				News news = new News();
				news.setNews_id(news_id);
				//생선된 news 인스턴스를 Comment DTO에 has a 관계로 밀어넣기!!
				comment.setNews(news);// 자식 DTO 가 부모 DTO를 보유하게 만듬
				list.add(comment);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			pool.freeConnection(con, pstmt, rs);
		}
		
		return list;
	}
	
	
}
