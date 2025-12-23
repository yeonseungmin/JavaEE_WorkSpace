package com.ch.shop.model.product;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.ch.shop.dto.Color;
import com.ch.shop.dto.Product;
import com.ch.shop.dto.ProductColor;
import com.ch.shop.dto.ProductSize;
import com.ch.shop.dto.Size;
import com.ch.shop.exception.ProductException;
import com.ch.shop.util.FileManager;

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
	@Autowired
	private ProductSizeDAO productSizeDAO;
	@Autowired
	private FileManager fileManager;
	
	// 상품이 등록될 외부 저장소의 루트 경로, 앞으로 상품이 등록 되면 상품의 pk 값을 따와서 디렉토리를 생성하고
	// 그 안에 파일들을 배치할 예정 ex) pk 값이 23 경우 C:\shopdata\product\p23\product-6.jpg
	private String rootDir ="C:\\shopdata\\product";
	
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
		
		/*------------------------------------------------------------
		 * 업무 3) ProductSize 테이블에 insert 하기
		 * -----------------------------------------------------------*/
		for(Size size : product.getSizeList()) {
			ProductSize productSize = new ProductSize();
			productSize.setProduct(product);
			productSize.setSize(size);
			productSizeDAO.insert(productSize);
		}
		/*------------------------------------------------------------
		 * 업무 4) 파일 저장 (트랜잭션의 대상이 되지 않지만, 크게보면 등록업무의 일부이므로 포함시켜버리자)
		 * -----------------------------------------------------------*/		
		//파일의 수가 여러개일 경우 , 파일저장 과정에서 만일 에러가 발생하면, 데이터베이스는 Service에 의해 자동으로 롤백 처리되지만.
		//파일에 대해서는 스프링이 관여하지 않는다. 따라서 실패 시 파일의 찌꺼기가 남게된다.
		// 해결책? 개발자가 트랜잭션 실패시, 파일을 직접 제거해야 함...
		// (디렉토리 별로 따로 만들어서 디렉토리 채로 제거하자 ex : p1223, p1224)
		String dirName=rootDir + "/P" +product.getProduct_id();
		fileManager.makeDirectory(dirName);
		
//		for(MultipartFile multipartFile : product.getPhoto()) { // 사용자가 업로드한 파일 수만큼 반복하면서 , FileManager
//			fileManager.save(multipartFile,"C:/shopdata/product" , null);
//		}

	}
}
