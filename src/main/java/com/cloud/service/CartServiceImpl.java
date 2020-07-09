package com.cloud.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloud.dao.BookDao;
import com.cloud.dao.CartDao;
import com.cloud.dao.UserDao;
import com.cloud.model.Book;
import com.cloud.model.Cart;
import com.cloud.model.User;
import com.timgroup.statsd.StatsDClient;

@Service
public class CartServiceImpl {
	
	@Autowired
	private CartDao cartDao;
	
	@Autowired
	private BookDao bookDao;
	
	@Autowired UserDao userDao;
	
	@Autowired
	private StatsDClient stats;
	
	private final Logger logger = LoggerFactory.getLogger(CartServiceImpl.class);

	public Cart addToCart(Cart inputCart, String userName) {
		try {
			long timeStarted = System.currentTimeMillis();
			Cart findCart = cartDao.findExistingCart(userName,inputCart.getBook());
			long timeEnded = System.currentTimeMillis();
            long diff = (timeEnded - timeStarted);
            stats.recordExecutionTime("Find CartList in ",diff);
			if(findCart==null) {
			
			Cart cart = new Cart();
			cart.setCartQuantity(inputCart.getCartQuantity());
			
			cart.setUserName(userName);
			cart.setCartStatus(true);
		 
			cart.setBook(inputCart.getBook());
			
			long timeStart = System.currentTimeMillis();
			Cart returnCart = cartDao.save(cart);
			long timeEnd = System.currentTimeMillis();
            long diffTime = (timeEnded - timeStarted);
            stats.recordExecutionTime("Added to Cart in ",diffTime);
			logger.info("add to cart successfull");
			return returnCart;
			}else {
				return null;
			}
			
		}catch(Exception e) {
			logger.error("error in add to cart");
			return null;
		}
	}

	public List<Cart> getCartList(String userName) {
		// TODO Auto-generated method stub
		try {
			long timeStarted = System.currentTimeMillis();
			List<Cart> cartList = cartDao.getCartList(userName);
			long timeEnded = System.currentTimeMillis();
            long diff = (timeEnded - timeStarted);
            stats.recordExecutionTime("Find CartList in ",diff);
			logger.info("get cart list successful");
			return cartList;
		}catch(Exception e) {
			logger.error("get cart list error");
		return null;
		}
	}

	public Cart cartUpdate(Cart cart) {
		// TODO Auto-generated method stub
		try {
			long timeStarted = System.currentTimeMillis();
		Cart findCart = cartDao.findExistingCart(cart.getUserName(),cart.getBook());
		long timeEnded = System.currentTimeMillis();
        long diff = (timeEnded - timeStarted);
        stats.recordExecutionTime("Find CartList in ",diff);
			findCart.setCartQuantity(cart.getCartQuantity());
			findCart.setCartStatus(true);
			long timeStart = System.currentTimeMillis();
			Cart returnCart = cartDao.save(findCart);
			long timeEnd = System.currentTimeMillis();
            long diffTime = (timeEnded - timeStarted);
            stats.recordExecutionTime("Updated to Cart in ",diffTime);
			logger.info("cart update successful");
			return returnCart;

		}catch(Exception e) {
			logger.error("cart update error");
			return null;
		}
	}

	public Cart cartRemove(Cart cart) {
		// TODO Auto-generated method stub
		try {
			long timeStarted = System.currentTimeMillis();
			Cart findCart = cartDao.findExistingCart(cart.getUserName(),cart.getBook());
			long timeEnded = System.currentTimeMillis();
	        long diff = (timeEnded - timeStarted);
	        stats.recordExecutionTime("Find CartList in ",diff);
			if(findCart!=null) {
				findCart.setCartStatus(false);
				long timeStart = System.currentTimeMillis();
				Cart returnCart = cartDao.save(findCart);
				long timeEnd = System.currentTimeMillis();
	            long diffTime = (timeEnded - timeStarted);
	            stats.recordExecutionTime("Updated to Cart in ",diffTime);
				logger.info("cart removal successful");
				return returnCart;
			}
		}catch(Exception e) {
			logger.error("cart removal error");
			return null;
		}
		return null;
	}

}
