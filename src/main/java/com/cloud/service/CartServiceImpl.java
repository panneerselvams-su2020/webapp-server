package com.cloud.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloud.dao.BookDao;
import com.cloud.dao.CartDao;
import com.cloud.dao.UserDao;
import com.cloud.model.Book;
import com.cloud.model.Cart;
import com.cloud.model.User;

@Service
public class CartServiceImpl {
	
	@Autowired
	private CartDao cartDao;
	
	@Autowired
	private BookDao bookDao;
	
	@Autowired UserDao userDao;
	


	public Cart addToCart(Cart inputCart, String userName) {
		try {
			Cart findCart = cartDao.findExistingCart(userName,inputCart.getBook());
			if(findCart==null) {
			
			Cart cart = new Cart();
			cart.setCartQuantity(inputCart.getCartQuantity());
			
			cart.setUserName(userName);
			cart.setCartStatus(true);
		 
			cart.setBook(inputCart.getBook());
			
			Cart returnCart = cartDao.save(cart);
			return returnCart;
			}else {
				return null;
			}
			
		}catch(Exception e) {
			return null;
		}
	}

	public List<Cart> getCartList(String userName) {
		// TODO Auto-generated method stub
		try {
			List<Cart> cartList = cartDao.getCartList(userName);
			return cartList;
		}catch(Exception e) {
		return null;
		}
	}

	public Cart cartUpdate(Cart cart) {
		// TODO Auto-generated method stub
		try {
			System.out.println(cart);
		Cart findCart = cartDao.findExistingCart(cart.getUserName(),cart.getBook());
			findCart.setCartQuantity(cart.getCartQuantity());
			findCart.setCartStatus(true);
			Cart returnCart = cartDao.save(findCart);
			return returnCart;

		}catch(Exception e) {
			System.out.println(e);
			return null;
		}
	}

	public Cart cartRemove(Cart cart) {
		// TODO Auto-generated method stub
		try {
			
			Cart findCart = cartDao.findExistingCart(cart.getUserName(),cart.getBook());
			if(findCart!=null) {
				findCart.setCartStatus(false);
				Cart returnCart = cartDao.save(findCart);
				return returnCart;
			}
		}catch(Exception e) {
			return null;
		}
		return null;
	}

}
