package com.ch.shop.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.ch.shop.model.topcategory.TopCategoryService;

/*
 * 쇼핑몰의 관리자에서 상품과 관련된 요청을 처리하는 하위 컨트롤러
 * */
@Controller 
public class ProductController {

	//서비스 보유 
	@Autowired
	private TopCategoryService topCategoryService;
	
	//상품 등록 폼 요청 처리
	@GetMapping("/product/registform")
	public String getRegistForm(Model model) {
		//3단계 : 상품페이지에 출력할 상위 카테고리 가져오기
		//List topList = topCategoryService.getList();	//이것도 가능하지만 비동기를 배웠으니
		
		//4단계 : 결과 저장(request에 직접 해야하지만, Spring 에서는 Model 객체를 이용하면 간접적으로 저장이 됨) 
		//jsp까지 topList를 살려서 가야하므로, 포워딩 처리를 해야 함.. 스프링 개발자가 redirect 를 명시하지 않으면 디폴트가 포워딩
		//model.addAttribute("topList",topList);	//위 주석과 동일한 효과
		
		
		return "admin/product/regist";
	}
}
