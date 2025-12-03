package com.ch.model1.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;

import com.ch.model1.util.PoolManager;

public class Member2DAO {
	PoolManager pool = new PoolManager();
	//insert - 레코드 1건
	public void insert() {
		Connection con;
		PreparedStatement pstmt;
		
		String sql = "insert into member2(id, name, email) values(?,?,?)";
		
	}

}
