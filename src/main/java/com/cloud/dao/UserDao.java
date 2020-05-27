package com.cloud.dao;

import org.hibernate.HibernateException;
import org.hibernate.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.cloud.model.User;





@Repository
public interface UserDao extends CrudRepository<User, String>{

	User findByUsername(String username);

//	@Query(value="select * from user where useremail=?",nativeQuery=true)
//	User checkuser(String useremail);
	
	

}

