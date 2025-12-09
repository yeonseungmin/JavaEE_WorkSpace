package com.ch.mvcframework.gui;



import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.ch.mvcframework.movie.model.MovieManager;

// 영화와 관련된 판단을 해주는 공통 로직을 jajvaSE 의 GuI프로그래밍에서도 사용할 수있는지 테스트해보자.
//테스트  성공한다면, 공통 로직의 작성을 성공한 것이고, 재사용가능성을 극대화 한 것임

public class MovieForm extends JFrame {// extends JFrame => is a Window
	
	JButton bt;
	JComboBox box;
	String[] movies= {"귀멸의 칼날","공각기동대","에이리언","소울"};
	MovieManager manager = new MovieManager();
	
	public MovieForm() {
		bt = new JButton("피드백 요청");
		box = new JComboBox();
		
		for(String movie : movies) {// 향상된  for 문
			box.addItem(movie);
			
		}
		
		// 부착전에 html 처럼 레이아웃을 먼저 설정
		setLayout(new FlowLayout());//FlowLayout 수평,수직의 직선으로 컴포넌트를 배치함, 윈도우 창에 따라 내용물들이 흘러다님

		this.add(box);//콤보박스를 윈도우에 부탁
		this.add(bt);//버튼을 윈도우에 부탁
		
		//버튼에 리스너 연결
		bt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String item = (String)box.getSelectedItem();
//				System.out.println(item);
				String msg = manager.getAdvice(item);
				JOptionPane.showMessageDialog(MovieForm.this, msg);
			}
		});
		
		setSize(300,200);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public static void main(String[] args) {
		//MovieForm 이 곧 윈도우 이므로 new하면 윈도우 창이 메모리에 생성됨(Why? is a 관계이므로)
		MovieForm movieForm = new MovieForm();
		
	}
	
}
