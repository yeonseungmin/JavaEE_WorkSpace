package com.ch.shop.model.product;

import org.springframework.stereotype.Component;

import com.ch.shop.dto.ProductImg;

@Component
public interface ProductImgDAO {
	public void insert(ProductImg productImg);
}
