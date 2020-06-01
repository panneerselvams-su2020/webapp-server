package com.cloud.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloud.dao.CartDao;
import com.cloud.dao.UserDao;
import com.cloud.model.Cart;

@Service
public class CartServiceImpl {
	
	@Autowired
	private CartDao cartDao;

	public Cart addToCart(String userName,int bookId) {
		try {
			return Cart;
		}catch(Exception e) {
			
		}
	}

	public List<Cart> getCartList(String userName) {
		// TODO Auto-generated method stub
		return null;
	}

	public Cart cartUpdate(Cart cart) {
		// TODO Auto-generated method stub
		return null;
	}

	public Cart cartRemove(Cart cart) {
		// TODO Auto-generated method stub
		return null;
	}

}
