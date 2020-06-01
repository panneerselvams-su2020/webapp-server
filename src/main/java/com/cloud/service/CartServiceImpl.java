package com.cloud.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloud.dao.CartDao;
import com.cloud.dao.UserDao;

@Service
public class CartServiceImpl {
	
	@Autowired
	private CartDao cartDao;

}
