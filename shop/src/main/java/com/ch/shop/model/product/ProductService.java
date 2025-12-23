package com.ch.shop.model.product;

import java.util.List;

import com.ch.shop.dto.Product;

public interface ProductService {
		public void regist(Product product);
		public List getList();	// 모든 목록 가져오기

		//우리의 경우, 이미지들을 상품의 pk값을 이용하여 "p23" 형식으로 pk값을 이요했기 때문에 디렉토리 삭제에 용이히다
		public void cancelUpload(Product product);
}
