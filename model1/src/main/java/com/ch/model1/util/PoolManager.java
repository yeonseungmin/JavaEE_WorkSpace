package com.ch.model1.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServlet;
import javax.sql.DataSource;

//DAO 의 각 메서드 마다 커넥션풀로부터 Connection 을 얻어오는 코드를 중복 작성할 경우 유지보수성이 떨어짐..
//예) JNDI 명칭이 바뀌거나, 연동할 DB의 종류가 바뀌는 등 ,, 외부의 어떤 변화원인에 의해 코드가 영향을 많이 받으면 안됨..
// 따라서 앞으로는 커넥션풀로부터 Connection 을 얻거나 반납하는 중복된 코드는 아래의 클래스로 처리하면 유지보수성이 올라감
public class PoolManager extends HttpServlet{
	private static PoolManager instance; //개발자들 사이에 통용되는 싱글톤 변수명 : instance 
	DataSource ds;
	private PoolManager() {
		try {
			InitialContext context = new InitialContext();
			ds=(DataSource)context.lookup("java:comp/env/jndi/mysql");
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static PoolManager getInstance() {
		//클래스변수인 instance 변수에 아무것도 존재하지 않을때만 직접 한번만 new 해준다.
		//PoolManager를 싱글턴으로 선언하면, 자바엔터프라자 개발에서 수많은 DAO들이
		//PoolManager를 매번 인스턴스 생성하는 낭비를 방지할 수 있다.
		if(instance == null) {
			instance = new PoolManager();
		}
		return instance;
	}
	
	//외부의 DAO 들이 직접 Connection을 얻는 코드를 작성하지 않게 하려면
	//이  PoolManager 에서 Connection을 얻어와서 반환해주자.
	public Connection getConnection() {
		Connection con=null;
		try {
			 con = ds.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return con;
	}
	
	//빌려간 커넥션을 반납
	public void freeConnection(Connection con) {
		if(con !=null) {
			try {
				// 주의 기존 JDBC 코드는 다 사용한 커넥션을 닫았지만, 풀로부터 얻어온 커넥션은 닫으면 안됨..
				con.close();
				// 이 객체는 DataSource 구현체로부터 얻어온 Connection 이기 때문에 close() 메서드에 의해
				// 일반적 JDBC의 닫는 close()가 아님
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	//아래의 오버로딩된 메서드는 DML 수행할때
	public void freeConnection(Connection con, PreparedStatement pstmt) {
		if(con !=null) {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(pstmt !=null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	// select 문 사용할경우
	public void freeConnection(Connection con, PreparedStatement pstmt, ResultSet rs) {
		if(con !=null) {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(pstmt !=null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(rs !=null) {
			try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
