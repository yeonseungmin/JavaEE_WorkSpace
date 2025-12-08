package com.ch.model1.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ch.model1.dto.News;
import com.ch.model1.util.PoolManager;


//News 테이블에 대한 CRUD만을 수행할 DAO
public class NewsDAO {
	//게시물 한건 넣기
	PoolManager pool = PoolManager.getInstance();
	public int insert(News news){
		Connection con = pool.getConnection();
		PreparedStatement pstmt =null;
		int result =0;
		String sql = "Insert into news(title,writer,content) values(?,?,?)";
		try {
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, news.getTitle());
			pstmt.setString(2, news.getWriter());
			pstmt.setString(3, news.getContent());
			
			result = pstmt.executeUpdate();
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			pool.freeConnection(con, pstmt);
		}
		return result;
	}
	
	//모든 레코드 가져오기
	public List<News> selectAll() {
		NewsDAO newsDAO = new NewsDAO();
		Connection con = pool.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs =null;
		List<News> list = new ArrayList();
		
		StringBuffer sb = new StringBuffer();
		sb.append("select n.news_id as news_id,title, writer,n.regdate,n.hit,count(c.msg) as cnt");
		sb.append(" from news n left outer join comment c");
		sb.append(" on n.news_id = c.news_id ");
		sb.append(" group by n.news_id, title , writer ,n.regdate ,n.hit");
		sb.append(" order by n.news_id desc;");
		
		System.out.println(sb.toString());
		
		String sql = "select * from news order by news_id asc";
		try {
			pstmt=con.prepareStatement(sb.toString());
			rs=pstmt.executeQuery();
	
			while(rs.next()) {
				News news = new News();
				news.setNews_id(rs.getInt("news_id"));
				news.setTitle(rs.getString("title"));
				news.setWriter(rs.getString("writer"));
//				news.setContent(rs.getString("content"));
				news.setRegdate(rs.getString("regdate"));
				news.setHit(rs.getInt("hit"));
				news.setCnt(rs.getInt("cnt"));
				
				list.add(news);
			}		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			pool.freeConnection(con, pstmt, rs);
		}
		
		return list;
	}
	
	public News select(int news_id) {
		NewsDAO newsDAO = new NewsDAO();
		Connection con = pool.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs =null;
		News dto =null;//한건 이므로
		
		String sql = "select * from news where news_id=?";
		try {
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1, news_id);
			rs=pstmt.executeQuery();
	
			if(rs.next()) {
				dto = new News();
				dto.setNews_id(rs.getInt("news_id"));
				dto.setTitle(rs.getString("title"));
				dto.setWriter(rs.getString("writer"));
				dto.setContent(rs.getString("content"));
				dto.setRegdate(rs.getString("regdate"));
				dto.setHit(rs.getInt("hit"));
				
			
			}		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			pool.freeConnection(con, pstmt, rs);
		}
		return dto;
	}
}
