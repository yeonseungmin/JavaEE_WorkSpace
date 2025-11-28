package com.ch.notice2.gui;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class RegistForm extends JFrame{
	/*	is a 	*/
	//클래스가 보유한 멤버변수가 객체형일 경우 has a 관계
	JTextField title; // 제목입력 텍스트 박스
	JTextField writer;
	JTextArea content;
	JButton bt;
	
	//생성자의 목적은 , 이 객체의 인스턴스가 생성될때 초기화할 작업이 있을경우
	// 초기화 작업을 지원하기 위함
	public RegistForm() {
		title = new JTextField(30); // 텍스트 박스의 디자인 길이(글자수)
		writer = new JTextField(30); // 텍스트 박스의 디자인 길이(글자수)
		content = new JTextArea(10,30); // 텍스트 박스의 디자인 길이(행,열)
		bt = new JButton("등록");
		
		//컴포넌트를 부착하기전에, 레이아웃을 결정짓자 css div 로 레이아웃 적용과 비슷
		setLayout(new FlowLayout()); //
		
		setSize(400,300);	// 너비와 높이를 부여
		this.add(title);
		this.add(writer);
		this.add(content);
		this.add(bt);
		
		this.setVisible(true);	//디폴트가 안보이므로 , 보이게
		
		//버튼에 클릭이벤트 구현하기
		bt.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				regist();
				
			}
		});
	}
	// 게시물 등록
	public void regist() {
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
			pstmt.setString(1,title.getText() );
			pstmt.setString(2, writer.getText());
			pstmt.setString(3, content.getText());
			
			int result = pstmt.executeUpdate();
			if(result <1) {
				JOptionPane.showMessageDialog(this, "실패");
			}else {
				JOptionPane.showMessageDialog(this, "성공");
			}
			
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로드 실패");
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
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
		
		//자원해제
	}
	
	public static void main(String[] args) {
		RegistForm win= new RegistForm();
		
	}
}
