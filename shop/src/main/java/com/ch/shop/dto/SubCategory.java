package com.ch.shop.dto;

import lombok.Data;

@Data
public class SubCategory {
	private int subcategory_id;
	private String subname;
	
	//부모 보유	(데이터 베이스 상에서의 관계는 부모와 자식을 id 로 연결시키지만, 
	//OOP언어 에서는 has a 관계인 객체로 연결)
	private TopCategory topCategory;
}
