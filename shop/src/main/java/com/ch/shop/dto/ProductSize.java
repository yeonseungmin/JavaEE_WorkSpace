package com.ch.shop.dto;

import lombok.Data;

@Data
public class ProductSize {

	private int product_size_id;
	private Product product; //oop 에서는 부모는 숫자가 아닌 객체이다.
	private Size size; 
	
}
