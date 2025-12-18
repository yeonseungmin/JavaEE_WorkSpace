package com.ch.shop.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ch.shop.dto.SubCategory;
import com.ch.shop.model.subcategory.SubCategoryService;

import lombok.extern.slf4j.Slf4j;

//쇼핑몰의 하위 카테고리에 대한 요청을 처리하는 하위 컨트롤러
@Controller
@Slf4j
public class SubCategoryController {
	
	@Autowired
	private SubCategoryService subCategoryService;
	
	// 목록 요청 처리
	// 주의 ) 클라이언트가 비동기 요청을 시도할 경우 서버는 절대로 JSP ,HTML 문서를 원하는것이 아님으로 데이터만 보내줘야한다.
	// 			특히 개발에 표준적으로 많이 사용되는 형식이 JSON 문자열
	@GetMapping("/admin/subcategory/list")
	@ResponseBody		// 리턴값 자체를 응답 정보로 사용하라고!!(DispatcherServlet에게 명령)
								// 또한 이 @ResponseBody를 적용하면 json MessageConvert를 자동적 적용!!
	public List<SubCategory> getList(int topcategory_id) {
		/*
		 * 클라이언트가 비동기 요청을 시도했음으로, 서버측의 하위 컨트롤러는 jsp 매핑을 해서는 안되며,
		 * DispatcherServlet 에게 더이상 return 시킨 정보에 대해 jsp 매핑이 아니라 , 반환시킨 값 그 자체를 응답정보로 사용하라고 부탁해야함
		 * */
		List subList = subCategoryService.getList(topcategory_id);
		
		log.debug("하위카테고리 목록 요청을 받음" + subList);
		
		// 자바 객체를 JSON 문자열로 자동으로 컴파일 해주는 라이브러리 Jackson 라이브러리를 이용하면 JSON 으로 변경해줌
		
		
		
		return subList;
	}
}
