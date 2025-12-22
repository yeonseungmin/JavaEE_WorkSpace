package com.ch.shop.dto;

import lombok.Data;

@Data
public class ProductColor {

	private int product_color_id;
	private Product product; //oop 에서는 부모는 숫자가 아닌 객체이다.
	private Color color; 
}
