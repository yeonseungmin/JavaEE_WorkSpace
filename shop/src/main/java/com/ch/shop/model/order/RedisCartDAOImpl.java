package com.ch.shop.model.order;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.ch.shop.dto.Cart;
import com.ch.shop.exception.CartException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class RedisCartDAOImpl implements RedisCartDAO {
	
	private static final String CART_KEY_PREFIX="cart:";	//장바구니의 key로 사용될 접두어
	/*
	 * Redis 는 테이블을 지원하지 않는 Key - Value 값을 저장해놓는 저장소, 
	 * 장바구니와 관련된 데이터를 넣을려면 장바구니임을 표시해야함
	 * 계획/설계 ) cart:member_id(누가) 	product_id(어떤상품을?) 	ea(몇개나) 
	 */
	
	@Autowired
	private RedisTemplate<String, String> redisTemplate;
	
	//이 메서드를 호출하면 cart:23, cart:34 등의 키값을 만들고 반환해줌
	private String getCartKey(int member_id) {
		return CART_KEY_PREFIX+member_id;
	}
	
	@Override
	public void addItem(Cart cart) throws CartException{
		//장바구니에서 넘어온 값이 갯수가 0이라면 .. 작업중단
		if(cart.getEa() <= 0) {
			throw new CartException("수량은 1개 이상 이어야 합니다.");
		}
		/* HGET ,HSET 등의 명령을 수행하는 객체 생성 */
							/* key 	field		value									객체 올리는 매서드 */
		HashOperations<String, String, String> hashOps= redisTemplate.opsForHash();
		
		String key = getCartKey(cart.getMember_id());	//장바구니에 사용될 키
										/*  문자열로 변환 */
		try {
			log.debug("Redis에 들어갈 데이터는 {} {} {}",key,cart.getProduct_id(),(long)cart.getEa());
			Long qnt=  hashOps.increment(key,Integer.toString(cart.getProduct_id()),(long)cart.getEa());
			if(qnt <=0) {
				throw new CartException("장바구니 수량이 유효하지 않습니다.");
			}
		}catch(CartException e){	// 비지니스 업무적 예외
			throw e;
		} catch (Exception e) { // 시스템 관련 비즈니스 업무 의외의 예외
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new CartException("장바구니 상품 과정중 오류발생",e);
		}
	}

	@Override
	public Map<Integer, Integer> getCart(Cart cart) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(Cart cart) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void remove(Cart cart) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeAll(Cart cart) {
		// TODO Auto-generated method stub
		
	}

	
}
