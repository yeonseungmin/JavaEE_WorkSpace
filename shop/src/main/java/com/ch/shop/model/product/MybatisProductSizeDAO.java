package com.ch.shop.model.product;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ch.shop.dto.ProductSize;
import com.ch.shop.exception.ProductColorException;
import com.ch.shop.exception.ProductSizeException;

@Repository
public class MybatisProductSizeDAO implements ProductSizeDAO{

	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	
	@Override
	public void insert(ProductSize productsize) throws ProductColorException {
		try {
			sqlSessionTemplate.insert("ProductSize.insert",productsize);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ProductSizeException("상품지원 색상 insert 실패",e);
		}
		
	}

}
