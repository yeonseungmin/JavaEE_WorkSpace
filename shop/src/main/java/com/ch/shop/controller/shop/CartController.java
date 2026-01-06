package com.ch.shop.controller.shop;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ch.shop.dto.Cart;
import com.ch.shop.dto.Member;
import com.ch.shop.dto.ResponseMessage;

@Controller
public class CartController {

	// 장바구니 메인 요청 처리
	@GetMapping("/cart/main")
	public String getMain(HttpSession session) {
		
		String viewName ="";
		
		//로그인 세션 
		Member member = (Member)session.getAttribute("member");
		
		if(member == null) {	//로그인 하지 않은경우
			viewName="shop/member/login";	//로그인 폼을 보여줌
		}else {	// 로그인 한 경우
			viewName="shop/cart/list";	//장바구니를 보여줌
		}
		
		return viewName;
	}

	
	/*
	 * 장바구니는 임시 저장 기능이므로, 다음의 3가지 기술로 구현이 가능하다.
	 * 1) 세션 - 별도의 DB가 필요없이 메모리상에서 구현이 간단하다
	 * 				단점 - 만일 이 쇼핑몰을 분산환경으로 구현하면, 하나의 쇼핑몰을 여러대의 서버가 구동하여 운영되므로
	 * 						세션이 공유 될 수 없다.. 사용자가 많을 경우 메모리가 너무 많이 쓰임..
	 * 						소규모나 테스트, 연구 분야에 사용... 실 서버 운영시에 부적합
	 * 				장점 - 개발자가 별도로 장바구니를 삭제하지 않아도 세션 소멸시 자동으로 소멸 된다.
	 * 
	 * 2) DB에 저장 - 장점 - 원하는 기간 만큼 제한없이 저장해 놓을 수 있음
	 * 					  단점 - 개발자가 주문이 완료되었을때 삭제하는 로직을 별도로 해야햄..
	 * 								따라서 사용자가 많을경우 DB 용량이 커짐
	 * 
	 * 3) Redis 데이터 베이스 - 가벼운 경량의 메모리 db이다. 기존의 전통적인 RDBMS 와는 달리, 테이블 등의 스키마가 존재하지 않음..
	 * 									(테이블 , 컬럼 등이 없음) Map 구조로 저장됨..
	 * 									장점 - 메모리 상의 db 임으로 속도가 빠르다.
	 * 											데이터의 유효기간을 명시할 수 있으므로 개발자가 별도의 삭제 작업 불필요( like cookie)	
	 * 									단점 - 메모리 용량 많이 차지
	 * 									
	 * */
	@GetMapping("/cart/add")
	@ResponseBody	// 반환값을 순수한 데이터로 보내는 어노테이션... 이게없다면 WEB-INF/view/"등록성공".jsp 로 매핑을 시도 할것임
	public ResponseEntity<ResponseMessage> addCart(@RequestParam(defaultValue = "0") int product_id,HttpSession session) {
		
		//클라이언트가 전송한 상품의 product_id 와 , 갯수를 이용하여  Cart (dto) 생성하고 보관
		// 그리고 이 생성된 Cart (dto)인스턴스를 세션에 저장..
		Cart cart = new Cart();
		
		cart.setProduct_id(product_id);
		
		cart.setEa(product_id); //넘겨받을 예정
		cart.setProduct_name(null); //넘겨받을 예정
		cart.setFilename(null);
		cart.setPrice(0);
		
		Map map = new HashMap<Integer, Cart>();	// 맵형으로 세션에
		session.setAttribute("cart", map);		//세션에 집어넣음
		
		ResponseMessage msg = new ResponseMessage();
		msg.setMsg("장바구니에 상품이 담겼습니다.");
		// 이 시점에 jackson 라이브러리를 개발자가 직접 사용하는것 이 아니라 , ResponseBody 에 의해 내부적으로 작동함
		
		// 스프링에서 지원하는 http 응답 전용 객체 , (head,body 구성해줌)
		//ResponseEntity<ResponseMessage>	반환형 자리에 교체
		// ResponseEntitiy.ok() 헤더값 200 바디값(msg)
		return ResponseEntity.ok(msg);
	}
	
}
