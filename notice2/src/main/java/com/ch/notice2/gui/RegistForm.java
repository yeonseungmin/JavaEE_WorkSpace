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

import com.ch.notice2.domain.NoticeDTO;
import com.ch.notice2.repository.NoticeDAO;

public class RegistForm extends JFrame{
	/*	is a 	*/
	//클래스가 보유한 멤버변수가 객체형일 경우 has a 관계
	JTextField title; // 제목입력 텍스트 박스
	JTextField writer;
	JTextArea content;
	JButton bt;
	NoticeDAO dao;// 오직 table에 대해 CRUD만을 처리하는 객체를 보유한다.!!
	
	//생성자의 목적은 , 이 객체의 인스턴스가 생성될때 초기화할 작업이 있을경우
	// 초기화 작업을 지원하기 위함
	public RegistForm() {
		title = new JTextField(30); // 텍스트 박스의 디자인 길이(글자수)
		writer = new JTextField(30); // 텍스트 박스의 디자인 길이(글자수)
		content = new JTextArea(10,30); // 텍스트 박스의 디자인 길이(행,열)
		bt = new JButton("등록");
		dao = new NoticeDAO();
		
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
		NoticeDTO notice = new NoticeDTO();//empty
		notice.setTitle(title.getText());
		notice.setWriter(writer.getText());
		notice.setContent(content.getText());
		
		int result = dao.regist(notice); //db에 insert !!
		if(result <1) {
			JOptionPane.showMessageDialog(this, "실패");
		}else {
			JOptionPane.showMessageDialog(this, "성공");
		}
	}
	
	public static void main(String[] args) {
		RegistForm win= new RegistForm();
		
	}
}
