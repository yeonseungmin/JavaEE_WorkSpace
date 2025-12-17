package com.ch.shop.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/*
 * 쇼핑몰의 관리자에서 상품과 관련된 요청을 처리하는 하위 컨트롤러
 * */
@Controller
public class ProductController {

	//상품 등록 폼 요청 처리
	@GetMapping("/admin/product/registform")
	public String getRegistForm() {
		//3단계 무
		//4단계 무
		return "/admin/product/regist";
	}
}
