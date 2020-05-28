package com.cloud.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cloud.service.UserServiceImpl;
import com.cloud.model.Password;
import com.cloud.model.User;

@RestController
public class UserController {
	
	@Autowired
	private UserServiceImpl  userService;

	
	@PostMapping("/signup")
	public ResponseEntity<User> save(@RequestBody User userObj) {
		
		User user = userService.save(userObj);
		
		if( user== null) {
			return ResponseEntity.badRequest().body(user);
		}
		else {
			return ResponseEntity.ok(user);
		}
	}
	
	
	@PutMapping("/update")
	public ResponseEntity<User> update(@RequestBody User userObj) {
		
		System.out.println(userObj);
		User user = userService.update(userObj);
		
		if(user == null) {
			return ResponseEntity.badRequest().body(user);
		}
		else {
			return ResponseEntity.ok(user);
		}
	}
	
	@PostMapping("/login")
	public ResponseEntity<User> userLogin(@RequestBody User userObj){
		User user = userService.userLogin(userObj);
		
		
			return ResponseEntity.ok(user);
		
	}
	
	@PutMapping("/updatePassword")
	public ResponseEntity<User> update(@RequestBody Password passwordObj) {
		
		User user = userService.updatePassword(passwordObj);
		
		if(user == null) {
			return ResponseEntity.badRequest().body(user);
		}
		else {
			return ResponseEntity.ok(user);
		}
	}
	
}
