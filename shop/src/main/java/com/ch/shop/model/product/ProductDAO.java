package com.ch.shop.model.product;

import java.util.List;

import com.ch.shop.dto.Product;

public interface ProductDAO {
	public void insert(Product product);
	public List selectAll();
}
