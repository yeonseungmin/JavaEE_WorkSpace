package com.ch.shop.controller.shop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ch.shop.dto.Cart;
import com.ch.shop.dto.Member;
import com.ch.shop.dto.ResponseMessage;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class CartController {
	
	//장바구니 목록 요청 처리 
	@GetMapping("/cart/main")
	public String getMain(HttpSession session, Model model) {
		//3단계: 세션에 들어있는 cart 라는 key를 갖는 객체들을  List형태로 바꿔서 전달해주자 jsp까지 전달해야 함..
		Map<Integer, Cart> cart=(Map)session.getAttribute("cart");
		
		List cartList = new ArrayList();//맵은 순서가 없으므로, 아래의 반복문을 이용하여 꺼내진 Cart DTO를 리스트에 담자
		
		for(Map.Entry<Integer, Cart> entry  :  cart.entrySet()) {
			log.debug("키는 {}, 값은 {}", entry.getKey(), entry.getValue());
			cartList.add(entry.getValue());
		}
		
		//4단계: jsp에서 보여질 결과 저장
		model.addAttribute("cartList", cartList);
		
		return "shop/cart/list";
	}
	
	/*
	 * 장바구니는 임시 저장 기능 이므로, 다음의 3가지 기술로 구현이 가능하다 
	 * 1) session - 별도의 db가 필요없으며, 메모리상에서 구현 가능 
	 * 				      단점 - 만일 이 쇼핑몰을 분산환경으로 구현하면, 하나의 쇼핑몰을 여러대의 서버가 구동하여 
	 *                            운영되므로, 세션이 공유될 수 없다..사용자가 많을 경우 메모리가 너무 많이 쓰임..
	 *                            소규모나 테스트, 연구분야에 사용..실 서버 운영 시 사용되지 않음
	 *                   장점 - 개발자가 별도로 장바구니를 지우지 않아도, 세션 소멸시 자동으로 처리됨..
	 * 2) DB에 저장- 장점: 원하는 기간만큼 제한없이 저장해놓을 수 있음
                            단점: 개발자가 주문이 완료되었을때, 삭제하는 처리를 별도로 해야 함 ..
                                   따라서 사용자가 많을 경우 데이터베이스 용량이 커짐..
        3) Redis 데이터베이스 - 가벼운 경량의 메모리 db이다. 기존의 전통적인 RDBMS 와는 달리, 테이블 등의 
                                          스키마가 존재하지 않음(테이블, 컬럼 등이 없음)
                                         Map 구조로 저장됨..
                                         장점 - 메모리상의 데이터베이스 이므로, 속도가 무지 빠름...
                                                 데이터의 유효기간을 명시할 수 있으므로, 개발자가 별도의 삭제 작업을 하지 
                                                 않아도 됨(마침 쿠키처럼)
                                         단점 - 메모리 용량이 많이 차지                                                                                                          		
	 * */
	@PostMapping("/cart/add")
	@ResponseBody //만일 @ResponseBody 가 붙어있지 않으면?  DispatcherServlet은 InternalResourceViewResolver에게 리턴된 스트링값을 이용하여 
							//실제 jsp를 얻어오려고 할 것이다  ex) WEB-INF/views/등록성공.jsp 
	public ResponseEntity<ResponseMessage> addCart(Cart cart , HttpSession session) {
		//장바구니에 담게될 정보 중 누가? 에 해당하는 member_id는 클라이언트의 브라우저로 보안상 넘겨받지 말고, 
		//세션에서 꺼내서 Cart DTO에 담자주자 
		Member member=(Member)session.getAttribute("member");
		log.debug("현재 세션의 Member DTO안에 들어있는 member_id는 {}", member.getMember_id());
		cart.setMember_id(member.getMember_id());
		
		log.debug("product_id ={}", cart.getProduct_id());
		log.debug("product_name ={}", cart.getProduct_name());
		log.debug("price ={}", cart.getPrice());
		log.debug("ea ={}", cart.getEa());
		
		//세션에 cart 라는 Key 가 존재하는지 먼저 따져보고 없다면 생성, 있다면 기존것에 추가
		Map<Integer,Cart> map = null;
		
		if(session.getAttribute("cart") == null) {
			map = new HashMap<>();
			session.setAttribute("cart", map);
		}else {
			map =(Map)session.getAttribute("cart");
			
		}
		//꺼내온 맵 내에서도 , 이미 등록된 상품이면 갯수만 증가시켜야하고, 등록되지 않은 상품이면 새로 등록
		Cart cartafter=(Cart)map.get(cart.getProduct_id());
		if(cartafter == null) { //아직 장바구니에 등록된적 없는 상품
			map.put(cart.getProduct_id(), cart);
		}else {//이미 등록된적이 있는 상품	(갯수 누적 증가)
			cartafter.setEa(cartafter.getEa()+cart.getEa());
		}
		
		
		
		ResponseMessage msg = new ResponseMessage();
		msg.setMsg("장바구니에 상품이 담겼습니다");
		//이 시점에 jackson 라이브러리를 개발자가 직접 사용하는것 아니라, @ResponseBody에 의해 내부적으로 작동함 
		
		//스프링에서 지원하는 HTTP 응답 전용 객체(head+body 구성해줌 )
		return ResponseEntity.ok(msg);  
	} 
}