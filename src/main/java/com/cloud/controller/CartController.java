package com.cloud.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.cloud.model.Cart;
import com.cloud.service.CartServiceImpl;

public class CartController {

	
	@Autowired CartServiceImpl cartService;
	
	@PostMapping("/addToCart/{bookId}")
	public ResponseEntity<Cart> addToCart(Authentication auth, @RequestParam int bookId){
		
		String userName = auth.getName();
		Cart cart = cartService.addToCart(userName, bookId);
		
		return ResponseEntity.ok(cart);
	}
	
	@GetMapping("/viewCart")
	public ResponseEntity<List<Cart>> getCartList(Authentication auth){
		String userName = auth.getName();
		List<Cart> listOfCart = cartService.getCartList(userName);
		return ResponseEntity.ok(listOfCart);
	}
	
	@PutMapping("/updateCart")
	public ResponseEntity<Cart> cartUpdate(@RequestBody Cart cart){
		Cart updateCart = cartService.cartUpdate(cart);
		return ResponseEntity.ok(updateCart);
	}
	
	@PutMapping("/removeFromCart")
	public ResponseEntity<Cart> removeCart(@RequestBody Cart cart){
		Cart removeCart = cartService.cartRemove(cart);
		return ResponseEntity.ok(removeCart);
	}
}
