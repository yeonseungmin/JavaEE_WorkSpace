package com.ch.shop.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

	//관리자 모드의 메인인 대시보드 요청을 처리
	@GetMapping("/main")
	public String main() {
		//3단계 무-
		//4단계 무-
		
		return "admin/index";	
	}
	
	
	
	
	
}
