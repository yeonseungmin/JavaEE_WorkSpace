package com.ch.shop.dto;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import lombok.Data;

//장바구니에 담겨질 하나의 상품정보를 담을 객체
@Data
public class Cart {
	
	/* 장바구니에 출력할 내용을 담을 DTO */
	private int product_id;
	private String product_name;
	private String filename;
	private int price;
	private int ea;
	
}
