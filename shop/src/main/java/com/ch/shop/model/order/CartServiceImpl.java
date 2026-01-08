package com.ch.shop.model.order;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ch.shop.dto.Cart;
import com.ch.shop.dto.Product;
import com.ch.shop.exception.CartException;
import com.ch.shop.model.product.ProductDAO;

@Service
public class CartServiceImpl implements CartService{
	
	@Autowired
	private RedisCartDAO redisCartDAO;
	
	@Autowired
	private ProductDAO productDAO;
	
	@Override
	public void regist(Cart cart) throws CartException{
		redisCartDAO.addItem(cart);
	}

	/*
	 * DAO에서 반환된 데이터 형태가 Map이지만 , 컨트롤러에서 jackson을 사용하여  List를 json으로 변환할 예정이므로 
	 * 아래의 메서드에서 컨트롤러측에 반환될 데이터 형을 Map -> List로 변환하여 반환해주자 
	 */
	@Override
	public List getList(Cart cart) {
		
		Map<Integer, Integer> map=redisCartDAO.getCart(cart);
		
		List<Cart> cartList = new ArrayList();//비어있는 리스트 하나 생성
		
		//Map은 순서가 없기 때문에, 직접 반복문으로 그 요소를 접근할 수 없다.따라서 map.entrySet() 메서드로 Set으로 변환하자   
		for(Map.Entry<Integer, Integer> entry : map.entrySet()) {
			
			Cart obj = new Cart();	
			obj.setMember_id(cart.getMember_id());
			obj.setProduct_id(entry.getKey());
			obj.setEa(entry.getValue());
			
			//mysql 에서 가져오기 
			Product product=productDAO.select(entry.getKey()); //현재 엔트리에서 product_id 가 key 이므로 
			obj.setProduct_name(product.getProduct_name());
			obj.setPrice(product.getPrice());
			
			cartList.add(obj);
		}
		
		return cartList;
	}

	@Override
	public void update(Cart cart) {
		// TODO Auto-generated method stub
		
	}

	
	@Override
	public void remove(Cart cart) throws CartException {
		redisCartDAO.remove(cart);
	}

	@Override
	public void removeAll(Cart cart) {
		// TODO Auto-generated method stub
		
	}

}