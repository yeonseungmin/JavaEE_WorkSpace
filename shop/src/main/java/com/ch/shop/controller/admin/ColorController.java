package com.ch.shop.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ch.shop.dto.Color;
import com.ch.shop.model.color.ColorService;


//관리자 측의 색상과 관련된 요청을 처리하는 컨트롤러
@Controller
public class ColorController {
	
	@Autowired
	private ColorService colorService;
	
	@GetMapping("/color/list")	//모든 레코드 가져오기 !!!!!!!!!!!!!!
	//클라이언트가 비동기 방식으로 색상을 원하므로 ModelAndView 로 처리하면 안된다.
	//즉,순수한 데이터인 json 문자열로 보낼수 있는 java.List로 줘야한다.
	//		원래는 개발자가 String으로 응답문자열을 하나하나 보내야 하지만, 스프링에게 자바객체를 Json 문자열로 변환시키자
	//		이 기능을 구현하려면 ? @ResponseBody 를 명시하되, (jackson)컨버터(Converter)가 등록되어있어야한다.
	@ResponseBody
	public List<Color> getList(){
		
		return colorService.getList();
	}
	
}
