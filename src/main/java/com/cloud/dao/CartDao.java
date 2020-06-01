package com.cloud.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.cloud.model.Book;
import com.cloud.model.Cart;

@Repository
public interface CartDao extends CrudRepository<Cart, Integer> {

	@Query("select a from Cart a where a.userName=?1 and a.cartStatus=?2")
	Cart addToCart(String userName, String cartStatus);
	
	@Query("select a from Cart a where a.userName=?1 and a.cartStatus=1")
	List<Cart> getCartList(String userName);
	
	@Query("select a from Cart a where a.userName=?1")
	Cart findExistingCart(String userName);
}
