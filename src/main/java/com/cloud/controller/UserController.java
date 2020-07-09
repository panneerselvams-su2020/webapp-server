package com.cloud.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cloud.service.UserServiceImpl;
import com.timgroup.statsd.StatsDClient;
import com.cloud.model.Password;
import com.cloud.model.User;

@RestController
public class UserController {
	
	@Autowired
	private UserServiceImpl  userService;
	
	@Autowired
	private StatsDClient stats;
	
	private final Logger logger = LoggerFactory.getLogger(UserController.class);

	@CrossOrigin
	@PostMapping("/signup")
	public ResponseEntity<User> save(@RequestBody User userObj) {
		stats.incrementCounter("endpoint.user.addUser.http.post");
		long statsStart = System.currentTimeMillis();
		User user = userService.save(userObj);
		
		if( user== null) {
			long statsEnd = System.currentTimeMillis();
			long duration = (statsEnd - statsStart);
			stats.recordExecutionTime("AddUserApiCall",duration);
			return ResponseEntity.ok(user);
		}
		else {
			long statsEnd = System.currentTimeMillis();
			long duration = (statsEnd - statsStart);
			stats.recordExecutionTime("AddUserApiCall",duration);
			return ResponseEntity.ok(user);
		}
	}
	
	
	@PutMapping("/update")
	public ResponseEntity<User> update(@RequestBody User userObj) {
		
		stats.incrementCounter("endpoint.user.updateUser.http.put");
		long statsStart = System.currentTimeMillis();
		User user = userService.update(userObj);
		
		if(user == null) {
			long statsEnd = System.currentTimeMillis();
			long duration = (statsEnd - statsStart);
			stats.recordExecutionTime("UpdateUserApiCall",duration);
			return ResponseEntity.ok(user);
		}
		else {
			long statsEnd = System.currentTimeMillis();
			long duration = (statsEnd - statsStart);
			stats.recordExecutionTime("UpdateUserApiCall",duration);
			return ResponseEntity.ok(user);
		}
	}
	
	@PostMapping("/login")
	public ResponseEntity<User> userLogin(@RequestBody User userObj){
		stats.incrementCounter("endpoint.user.loginUser.http.post");
		long statsStart = System.currentTimeMillis();
		User user = userService.userLogin(userObj);
		long statsEnd = System.currentTimeMillis();
		long duration = (statsEnd - statsStart);
		stats.recordExecutionTime("LoginUserApiCall",duration);
		return ResponseEntity.ok(user);
		
	}
	
	@GetMapping("/user")
	public ResponseEntity<User> getUserToLogin(@RequestBody User userObj){
		stats.incrementCounter("endpoint.user.getUser.http.get");
		long statsStart = System.currentTimeMillis();
		User user = userService.getUser(userObj);
		if(user == null) {
			long statsEnd = System.currentTimeMillis();
			long duration = (statsEnd - statsStart);
			stats.recordExecutionTime("GetUserApiCall",duration);
			return ResponseEntity.ok(user);
		}
		else {
			long statsEnd = System.currentTimeMillis();
			long duration = (statsEnd - statsStart);
			stats.recordExecutionTime("GetUserApiCall",duration);
			return ResponseEntity.ok(user);
		}
	}
	
	@PutMapping("/updatePassword")
	public ResponseEntity<User> update(@RequestBody Password passwordObj) {
		stats.incrementCounter("endpoint.password.updatePassword.http.put");
		long statsStart = System.currentTimeMillis();
		User user = userService.updatePassword(passwordObj);
		
		if(user == null) {
			long statsEnd = System.currentTimeMillis();
			long duration = (statsEnd - statsStart);
			stats.recordExecutionTime("UpdatePasswordApiCall",duration);
			return ResponseEntity.ok(user);
		}
		else {
			long statsEnd = System.currentTimeMillis();
			long duration = (statsEnd - statsStart);
			stats.recordExecutionTime("UpdatePasswordApiCall",duration);
			return ResponseEntity.ok(user);
		}
	}
	
	@PutMapping(path="/testLogin")
	public ResponseEntity<String> authenticateLogin (@RequestBody User userObj) {
		
		String response = "";
	     
		String userName = userObj.getUserName();
		String userPassword = userObj.getUserPassword();
		
		
		if(userPassword == "12345678" && userName == "sridharprasad.p@gmail.com")
		{
			 response = "Login Success";
		}
		else {
			 response = "Authentication Failed";
		}

	        	 return ResponseEntity.ok(response);

	}
}
