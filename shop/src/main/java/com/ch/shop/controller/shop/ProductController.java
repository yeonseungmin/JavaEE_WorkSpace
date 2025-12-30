package com.ch.shop.controller.shop;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ch.shop.dto.Product;
import com.ch.shop.model.product.ProductService;

@Controller
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
	//상품 목록 요청 처리
	@GetMapping("/product/list")	//jsp 내부에서 사용할 경로
	public String getProductList(Model model, @RequestParam(name="subcategory_id",defaultValue = "0") int subcategory_id) {
		// 파라미터값이 0인 경우, 사용자는 모든 상품을 보고싶은것임.. 0이 아니면 해당 하위 카테고리에 소속된 상품만 나열됨
		List productList = productService.selectBySubCategoryId(subcategory_id);	//3단계
		model.addAttribute("productList",productList); //4단계
		
		
		
		return "shop/product/list";	//jsp 파일 위치 경로
	}
	
	
	//상품 상세 보기 요청 처리
	@GetMapping("/product/detail")
	public String getProductDetail(@RequestParam(defaultValue = "0") int product_id,Model model) {
		
		Product product = productService.select(product_id);//3단계 상세내용가져오기
		model.addAttribute("product",product); //4단계 detail.jsp 에서 보여질 Product 저장
		
		return "shop/product/detail";
	}
}
