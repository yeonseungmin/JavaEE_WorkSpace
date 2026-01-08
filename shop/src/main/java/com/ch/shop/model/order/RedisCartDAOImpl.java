package com.ch.shop.model.order;

import java.util.HashMap;
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
public class RedisCartDAOImpl implements RedisCartDAO{
	
	private static final String CART_KEY_PREFIX ="cart:";//장바구니의 키로 사용될 접두어

	/*
	 Redis는 테이블을 지원하지 않는 key - value 값만을 저장해놓는 저장소라서 장바구니와 관련된 데이터를 넣으려면
	 장바구니임을 표시해야 한다..
	 계획/설계) cart:member_id(누가?)   product_id(어떤 상품을?)   ea(몇개나?) 
	 */
	@Autowired
	private RedisTemplate<String, String> redisTemplate;
	
	//이 메서드를 호출하면 cart:23, cart:34 의 키값을 만들어주고 반환해줌 
	private String getCartKey(int member_id) {
		return CART_KEY_PREFIX+member_id;
	}
	
	@Override
	public void addItem(Cart cart) throws CartException{
		//장바구니에서 넘어온 값이 갯수가 0이라면..작업 중단 
		if(cart.getEa() <=0) {
			throw new CartException("수량은 1개 이상이어야 합니다");
		}
		
		/*HSET, HGET 등의 명령을 수행하는 객체 생성 */
							  /* key    field    value  */
		HashOperations<String, String, String> hashOps=redisTemplate.opsForHash();
		
		String key = getCartKey(cart.getMember_id());//장바구니에 사용될 키   
	
		try {
			log.debug("Redis에 들어갈 데이터는 {} {} {}", key, cart.getProduct_id(), cart.getEa());
			
			//cart:24  12  3
			Long result=hashOps.increment(key , Integer.toString( cart.getProduct_id()),  (long)cart.getEa());
			//increment() 메서드 호출 후 반환되는 result 란? 이 메서드에 의해 반영된 숫자값의 최종값  
			
			if(result <=0) {
				//만일 장바구니에 담겨진 갯수가 0이라면, 굳이 장바구니에 담아놓을 필요가 없으므로, 삭제시키자 
				hashOps.delete(key, Integer.toString(cart.getProduct_id()));
				
				throw new CartException("장바구니 수량이 유효하지 않습니다");
			}
		}catch(CartException e) { //비즈니스 업무적 예외..(예- 제대로 들어갔다, 안들어갔다..)
			throw e;
		}catch (Exception e) {//시스템 관련된 비즈니스 업무 이외의 예외
			e.printStackTrace();
			throw new CartException("장바구니 상품 등록 과정에 오류 발생", e);
		}
		
	}

	/*
	 * 장바구니 목록 처리   
	 * */
	@Override
	public Map<Integer, Integer> getCart(Cart cart) {
		
		String key = getCartKey(cart.getMember_id()); //cart:8 형식이 생성 
		
		//Redis 서버에 명령을 수행하기 위한 객체 얻기
		HashOperations<String, String, String> hashOps=redisTemplate.opsForHash();
		
		Map<String, String> entries = hashOps.entries(key); //key value 의 쌍으로 이루어진 엔트리들을 반환 ..
		
		//메서드 반환용 맵 
		Map<Integer, Integer> result = new HashMap<>();
		
		for(Map.Entry<String, String>  entry : entries.entrySet()) {
							//   prodcut_id 가 key 가 됨                 ea가 value 가 됨  
			result.put(Integer.parseInt(entry.getKey()) ,   Integer.parseInt(entry.getValue()));
		}
		
		return result;
	}

	@Override
	public void update(Cart cart) {
		// TODO Auto-generated method stub
		
	}

	//장바구니에서 상품 1건 삭제
	@Override
	public void remove(Cart cart) throws CartException{
		//인수 1 - 누구의 장바구니 인지를 결정하는 cart:10
		// 인수 2 - 어느 필드를 삭제할지를 결정하는 product_id
		try {
			String key = getCartKey(cart.getMember_id());
			Long deletedCount=redisTemplate.opsForHash().delete(key, Integer.toString(cart.getProduct_id()));
			log.debug("삭제 된 건수 {}",deletedCount);
			if(deletedCount ==null || deletedCount==0) {
				throw new CartException("삭제할 대상이 존재하지 않습니다.");
			}
		} catch(CartException e) {
			e.printStackTrace();
			throw e;
		}catch (Exception e) {
			throw new CartException("장바구니 삭제 과정에 오류 발생",e);
		}
	}

	@Override
	public void removeAll(Cart cart) {
		// TODO Auto-generated method stub
		
	}


}