package com.ch.shop.controller.shop;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.ch.shop.model.topcategory.TopCategoryService;

import lombok.extern.slf4j.Slf4j;

//일반 유저가 사용하게 될 쇼핑몰 쪽의 회원관련 요청을 처리하는 컨트롤러

@Slf4j
@Controller
public class MemberController {
	
	@Autowired
	private TopCategoryService topCategoryService;
	//회원 로그인폼 요청 처리
	@GetMapping("/member/loginform")
	public String getLoginForm(Model model) {
		List topList=topCategoryService.getList();//3단계
		
		//4단계 결과 페이지로 가져갈것이 있따.
		model.addAttribute("topList", topList);
		return "shop/member/login";
	}
	
}
