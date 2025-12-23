package com.ch.shop.dto;

import lombok.Data;

@Data
public class ProductImg {

	private int product_img_id;
	private String filename;
	private Product product; //oop 에서는 부모는 숫자가 아닌 객체이다.

}
