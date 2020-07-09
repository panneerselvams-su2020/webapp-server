package com.cloud.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cloud.model.Cart;
import com.cloud.service.CartServiceImpl;
import com.timgroup.statsd.StatsDClient;

@RestController
public class CartController {

	
	@Autowired 
	CartServiceImpl cartService;
	
	@Autowired
	private StatsDClient stats;
	
	private final Logger logger = LoggerFactory.getLogger(CartController.class);
	
	@PostMapping("/addToCart")
	public ResponseEntity<Cart> addToCart(Authentication auth, @RequestBody Cart cart){
		stats.incrementCounter("endpoint.cart.addToCart.http.post");
		long statsStart = System.currentTimeMillis();
		String userName = auth.getName();
		Cart newCart = cartService.addToCart(cart,userName);
		long statsEnd = System.currentTimeMillis();
		long duration = (statsEnd - statsStart);
		stats.recordExecutionTime("AddToCartApiCall",duration);
		return ResponseEntity.ok(newCart);
	}
	
	@GetMapping("/viewCart")
	public ResponseEntity<List<Cart>> getCartList(Authentication auth){
		stats.incrementCounter("endpoint.cart.listCart.http.get");
		long statsStart = System.currentTimeMillis();
		String userName = auth.getName();
		List<Cart> listOfCart = cartService.getCartList(userName);
		long statsEnd = System.currentTimeMillis();
		long duration = (statsEnd - statsStart);
		stats.recordExecutionTime("ListCartApiCall",duration);
		return ResponseEntity.ok(listOfCart);
	}
	
	@PutMapping("/updateCart")
	public ResponseEntity<Cart> cartUpdate(@RequestBody Cart cart){
		stats.incrementCounter("endpoint.cart.updateCart.http.put");
		long statsStart = System.currentTimeMillis();
		Cart updateCart = cartService.cartUpdate(cart);
		long statsEnd = System.currentTimeMillis();
		long duration = (statsEnd - statsStart);
		stats.recordExecutionTime("UpdateCartApiCall",duration);
		return ResponseEntity.ok(updateCart);
	}
	
	@PutMapping("/removeFromCart")
	public ResponseEntity<Cart> removeCart(@RequestBody Cart cart){
		stats.incrementCounter("endpoint.cart.removeFromCart.http.put");
		long statsStart = System.currentTimeMillis();
		Cart removeCart = cartService.cartRemove(cart);
		long statsEnd = System.currentTimeMillis();
		long duration = (statsEnd - statsStart);
		stats.recordExecutionTime("RemoveFromCartApiCall",duration);
		return ResponseEntity.ok(removeCart);
	}
}
