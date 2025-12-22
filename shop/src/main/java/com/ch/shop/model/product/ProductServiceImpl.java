package com.ch.shop.model.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ch.shop.dto.Color;
import com.ch.shop.dto.Product;
import com.ch.shop.dto.ProductColor;
import com.ch.shop.exception.ProductException;

import lombok.extern.slf4j.Slf4j;

/*
 * 서비스의 존재이유?
 * 1)컨트롤러가 모델 영역의 디테일한 업무를 하지 못하게 방지
 * 		만일 컨트롤러가 디테일한 업무를 하게 되면, 모델영역의 업무를 부담하게되므로, MVC경계가 무너져 버린다.
 * 		모델 영역을 분리시킬수 없으므로, 재사용성이 떨어지게 된다.
 * 
 * 2) 트랜잭션 처리 시 핵심 역할 담당
 * 		서비스는 직접 일하지는 않지만 모델 영역의 DAO 등에게 업무를 분담하는 능력을 가짐
 * 		특히 데이터 베이스와 관련되어서는 각 DAO 들의 업무 수행결과에 따라 트랜잭션을 commit or rollback 결정 짓는 주체!!
 * 
 * */
@Service
@Slf4j
public class ProductServiceImpl implements ProductService{

	@Autowired
    private ProductDAO productDAO; 
	@Autowired
	private ProductColorDAO productColorDAO;
	
	
	
	@Override
	// 등록 시 발생하는 예외를 여기서 잡아버리면, 서비스 영역에서 예외는 원인이 소멸되어 버림,, 
	// 우리의 목적은 개발자가 아닌 일반 사용자 까지 예외 원인을 전달하는게 목적이므로, 컨트롤러에게 까지 예외를 전달해야 한다.
	@Transactional
	public void regist(Product product) throws ProductException{
		
		// 상품 등록이라는 커다란 업무영역은 총 4가지의 세부 업무로 구성되어 있음
		
		/*------------------------------------------------------------
		 * 업무 1) Product 테이블에 insert 하기
		 * -----------------------------------------------------------*/
		log.debug("인서트 하기 직전의 pk="+product.getProduct_id());
		productDAO.insert(product);
		log.debug("인서트 직후의 pk="+product.getProduct_id());
		
		/*------------------------------------------------------------
		 * 업무 2) ProductColor 테이블에 insert 하기
		 * -----------------------------------------------------------*/
		for(Color color : product.getColorList()) {
			ProductColor productColor = new ProductColor();
			productColor.setProduct(product);
			productColor.setColor(color);
			productColorDAO.insert(productColor);
		}
		
		
		
		
	}
}
