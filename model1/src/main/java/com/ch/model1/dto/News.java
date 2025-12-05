package com.ch.model1.dto;

import java.util.List;

import lombok.Data;

@Data
public class News {
	private int news_id;
	private String title;
	private String writer;
	private String content;
	private String regdate;
	private int hit;
	//하나의 뉴스 기사는 다수의 자식을 보유할 수 있따.
	private List<Comment> commentList;
}
