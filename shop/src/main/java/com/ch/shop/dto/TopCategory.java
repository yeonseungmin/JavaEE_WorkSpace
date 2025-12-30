package com.ch.shop.dto;

import java.util.List;

import lombok.Data;

@Data
public class TopCategory {
	private int topcategory_id;
	private String topname;
	private List<SubCategory> subList;		// 자식의 정보를 리스트로 사용
}
