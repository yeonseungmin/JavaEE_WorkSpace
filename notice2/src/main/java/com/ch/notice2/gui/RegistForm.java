package com.ch.notice2.gui;

import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
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
	}
	
	public static void main(String[] args) {
		RegistForm win= new RegistForm();
		
	}
}
