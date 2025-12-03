package com.ch.model1.repository;

import java.security.interfaces.RSAKey;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ch.model1.dto.BoardDTO;
import com.ch.model1.dto.Member2DTO;
import com.ch.model1.util.PoolManager;

public class Member2DAO {
	PoolManager pool = PoolManager.getInstance();
	//insert - 레코드 1건
	public int insert(Member2DTO member2DTO) {
		Connection con =pool.getConnection();
		PreparedStatement pstmt =null;
		int result=0; // DML 수행후 그 결과를 받아놓을 변수
		
		String sql = "insert into member2(id, name, email) values(?,?,?)";
		try {
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, member2DTO.getId());
			pstmt.setString(2, member2DTO.getName());
			pstmt.setString(3, member2DTO.getEmail());
			result = pstmt.executeUpdate();
			 	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			pool.freeConnection(con, pstmt);
		}
		return result;
	}
	//select 모든레코드 갖고오기
	public List selectAll() {
		Connection con= pool.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs =null;
		List<Member2DTO> list = new ArrayList();
		
		String sql = "select* from member2 order by member2_id asc";
		try {
			pstmt=con.prepareStatement(sql);
			rs=pstmt.executeQuery();
			
			while(rs.next()) {
				Member2DTO member2dto = new Member2DTO();
				member2dto.setMember2_id(rs.getInt("member2_id"));
				member2dto.setId(rs.getString("id"));
				member2dto.setName(rs.getString("name"));
				member2dto.setEmail(rs.getString("email"));
				
				list.add(member2dto);
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
