package com.ch.shop.model.order;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ch.shop.dto.Cart;
import com.ch.shop.exception.CartException;

@Service
public class CartServiceImpl implements CartService{

	@Autowired
	private RedisCartDAO redisCartDAO;
	
	@Override
	public void regist(Cart cart) throws CartException{
		redisCartDAO.addItem(cart);
		
	}

	@Override
	public List getList(Cart cart) {
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
