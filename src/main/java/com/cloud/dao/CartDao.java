package com.cloud.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.cloud.model.Cart;

@Repository
public interface CartDao extends CrudRepository<Cart, String> {

	Cart findbyUserName(String userName);
}
