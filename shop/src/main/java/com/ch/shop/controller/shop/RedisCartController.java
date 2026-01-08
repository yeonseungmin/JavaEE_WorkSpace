package com.ch.shop.controller.shop;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ch.shop.dto.Cart;
import com.ch.shop.dto.Member;
import com.ch.shop.dto.ResponseMessage;
import com.ch.shop.exception.CartException;
import com.ch.shop.model.order.CartService;

import lombok.extern.slf4j.Slf4j;

/*세션 기반의 장바구니가 아닌 속도 빠른 메모리 기반 데이터베이스인 Redis 기반의 장바구니 요청을 처리하는 컨트롤러 */
@Slf4j
@Controller
public class RedisCartController {
	
	@Autowired
	private CartService cartService;
	
	//장바구니 등록 비동기 요청을 처리 
	@PostMapping("/cart/regist")
	@ResponseBody
	public ResponseEntity<ResponseMessage> regist(HttpSession session, Cart cart){
		Member member=(Member)session.getAttribute("member");
		
		cart.setMember_id(member.getMember_id());//누구의 장바구니 인지를 위함
		// Redis에 저장할 형식 중  cart:member_id   product_id   ea 중 key에 들어갈 값
		log.debug("member_id is {}", cart.getMember_id());
		log.debug("product_id is {}", cart.getProduct_id());
		log.debug("ea is {}", cart.getEa());
		
		//3단계: 일 시키기 
		cartService.regist(cart);
		
		ResponseMessage message = new ResponseMessage();
		message.setMsg("장바구니 등록 성공");
		
		return ResponseEntity.ok(message);
	}
	
	//장바구니 페이지 요청 처리 
	@GetMapping("/cart/list")
	public String getListPage() {
		return "shop/cart/list";
	}
	
	
	//장바구니 목록 비동기 요청 처리 
	@GetMapping("/cart/async/list")
	@ResponseBody //jackson library 알아서 List -->  json 배열로 자동변환 (convert)
	public List getList(HttpSession session) { //cart 매개변수의 역할? 누구의(member_id) 
		Member member = (Member)session.getAttribute("member");
		Cart cart = new Cart();
		cart.setMember_id(member.getMember_id()); //Member DTO로 부터 pk 값을 구하여 Cart DTO에 심어주기 
		return cartService.getList(cart);
	}
	
	//장바구니 아이템 1건 삭제 요청 처리
	@PostMapping("/cart/remove")
	@ResponseBody
	public ResponseEntity<ResponseMessage> remove(HttpSession session,Cart cart){
		//cart:10 48 1
		//어떤 회원의 장바구니를 삭제할지를 알아야 하므로 member_id를 추출해야함
		Member member = (Member)session.getAttribute("member");
		cart.setMember_id(member.getMember_id());
		
		//3단계 일시키키
		cartService.remove(cart);
		//아래의 클래스를 굳이 사용하는 이유 ? jackson 라이브러리가 객체를 대상으로 json으로 바꿔주기 때문에
		ResponseMessage message = new ResponseMessage();
		message.setMsg("동훈 짱 삭제 성공");
		return ResponseEntity.ok(message);
	}
	
	
	//컨트롤러의 모든 메서드 중, 예외가 발생할 경우엔 무조건 아래의 핸들러 메서드로 실행부가 진입
	@ExceptionHandler(CartException.class)
	public ResponseEntity<ResponseMessage> handle(CartException e){
		
		ResponseMessage message = new ResponseMessage();
		message.setMsg("장바구니 등록 실패");
		//서버에서 특별히 에러 상태코들르 보내지 않으면 클라이언트 ajax 측 에서 성공인 success가 동작할 수 있으므로, 
		//헤더값에 에러수준의 상태코드를 보내자 
		
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
	}
}