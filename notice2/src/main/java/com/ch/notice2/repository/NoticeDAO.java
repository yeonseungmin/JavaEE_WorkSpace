package com.ch.notice2.repository;
/*
 * 이 클래스의 목적?
 * javaEE 기반의 애플리케이션 이건, javaSE 기반의 애플리케이션 데이터베이스 를 연동하는 비지니스 로직은 동일
 * 따라서 유지보스성을 고려하여 여러 플랫폼에서도 재사용할 수 있는 객체를 정의
 * 특히 로직 객체중 오직 데이터베이스 연동을 전담하는 역할을 하는 객체를 가리켜 애플리케이션 설계분야에서는
 * DAO라고 한다.(Data Access Object) - DB에 테이블이 만일 5개라면 DAO로 1:1대응하여 5개를 만들어야한다.
 * 특히 데이터베이스의 테이블에 데이터를 처리하는 업부를 가리켜 CRUD라고 한다. 
 * 
 * 아래와 같은 메서드에서, 매개변수의 수가 많아질 경우, 코드가 복잡해진다..
 * 따라서 매개변수를 각각 낱개로 전달하는것이 아니라 , 객체안에 모두 넣어서, 객체 자체를 전달...
 * DTO(Data Transfer Object) 오직 데이터만을 보유한 전달객체를 의미 따라서, 로직은 없다.
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.ch.notice2.domain.NoticeDTO;

public class NoticeDAO {
	
	// 게시물 등록
	public int regist(NoticeDTO notice) {
		int result =0;// Insert 후 성공인지 실패인지를 판단할 수 있는 반환값
		
		Connection con = null;// 지역변수는 반드시 초기화 (컴파일러가 자동으로 초기화 하지않음)
		PreparedStatement pstmt = null;
		
		try {
			//드라이버 로드
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("드라이버로드 성공");
			System.out.println("동훈이 뿌잉 ><");
			System.out.println("태호 뿌잉 ><");
			//접속
			con= DriverManager.getConnection("jdbc:mysql://localhost:3306/java","servlet","1234");
			System.out.println(con);
			
			//쿼리날리기
			String sql = "insert into notice(title, writer, content) values(?,?,?)";
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1,notice.getTitle());
			pstmt.setString(2, notice.getWriter());
			pstmt.setString(3, notice.getContent());
			
			result = pstmt.executeUpdate();
			
			
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로드 실패");
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			//자원해제
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}if(con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return result;
	}
}
