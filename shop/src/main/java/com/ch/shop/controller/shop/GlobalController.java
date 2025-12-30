package com.ch.shop.controller.shop;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.ch.shop.model.topcategory.TopCategoryService;

/*
 * 목적 : 모든 컨트롤러 보다 앞서 실행되는 컨트롤러 정의
 * 			topCategory목록을 해결하여 코드 중복을 없앰!!
 * 	해결 : @ControllerAdvice 명시
 * 			ControllerAdvice 안에 작성된 메서드는 다른 컨트롤러의 실행에 앞서서 실행되어 지는데, 주의할 점은 이안에 정의한 모든메서드가
 * 			동작하는 것이 아니라,[ 아래의 3가지 어노테이션에 대해서만 효과가 발생 ]
 * 			1) @InitBinder	-	컨트롤러로 전달되는 파라미터에 대해 개발자가 커스텀하고 싶을때 사용
 * 			2) @ModelAttribute		-	Model 객체에 model.setAttribute("topList", topList); 의 효과를 낼 수있는 어노테이션
 * 			3) @ExceptionHandler		-	
 */

@ControllerAdvice
public class GlobalController {
	@Autowired
	private TopCategoryService topCategoryService;

	// 아래의 메서드는 위 3가지 어노테이션을 명시하지 않았음으로, 먼저 동작효과 X
	public void test() {
	}

	@ModelAttribute("topList") // 쇼핑몰의 상위 카테고리를 저장해놓기
	public List getTopCategoryList() {
		
//		 List topList=topCategoryService.getList();//3단계
//		 model.addAttribute("topList", topList);
		 
		return topCategoryService.getList();
	}

}
