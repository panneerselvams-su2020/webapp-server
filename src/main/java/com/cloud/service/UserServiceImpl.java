package com.cloud.service;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cloud.dao.UserDao;
import com.cloud.model.Password;
import com.cloud.model.User;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;


@Service
public class UserServiceImpl implements UserDetailsService {

	
	@Autowired
	private UserDao userDao;
	
	private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Transactional
	public User save(User user) {
		// TODO Auto-generated method stub
		
		if(!userDao.existsById(user.getUserName())) {
			String userPassword = user.getUserPassword();
			String decode = new String(Base64.getDecoder().decode(userPassword));
			String password = BCrypt.hashpw(decode,BCrypt.gensalt());
			user.setUserPassword(password);
			User returnUser = userDao.save(user);
			logger.info("user create successfull");
			return returnUser;
		}else {
			logger.error("user create error");
			return null;
		}

	}
	
	public User update(User user) {
		User userUpdate = userDao.findById(user.getUserName()).get();
		System.out.println(userUpdate);
		userUpdate.setFirstName(user.getFirstName());
		userUpdate.setLastName(user.getLastName());
		
		User user1 = userDao.save(userUpdate);
		System.out.println(user1);
		logger.info("user update successful");
		return user1;
		
	}
	
	public User getUser(User user) {
		User userDetails = userDao.findById(user.getUserName()).get();
		logger.info("get User success");
		return userDetails;
	}
	
	public User userLogin(User user) {
		User userlogin = userDao.findById(user.getUserName()).get();
		
		String enteredPassword = user.getUserPassword();
		String decode = new String(Base64.getDecoder().decode(enteredPassword));
		boolean matchPassword = BCrypt.checkpw(decode, userlogin.getUserPassword());
		
		if(matchPassword) {
			System.out.println("pwd matchws");
			System.out.println(userlogin.toString());
			logger.info("password matched");
			return userlogin;
		}else {
			System.out.println("unmatched");
			logger.error("password do not match");
			return null;
		}
		
		
}
	
	@Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userDao.findByUsername(username);
        if (user == null) {
        	logger.error("User not found");
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getUserPassword(),
                new ArrayList<>());
    }
	
	public User updatePassword(Password password) {
		User user = userDao.findByUsername(password.getUserName());
		
		String oldPassword = password.getOldPassword();
		String oldDbPassword = user.getUserPassword();
		String decode = new String(Base64.getDecoder().decode(oldPassword));
		boolean matchPassword = BCrypt.checkpw(decode, oldDbPassword);
		
		if(matchPassword==true) {
			String newPassword = password.getNewPassword();
			String decodeNew = new String(Base64.getDecoder().decode(newPassword));
			String finalPassword = BCrypt.hashpw(decodeNew,BCrypt.gensalt());
			user.setUserPassword(finalPassword);
			User finalUser = userDao.save(user);
			logger.info("password match in update");
			return finalUser;
			
		}
		logger.error("passwords dont match in update");
		return null;
	}
	

}

	
