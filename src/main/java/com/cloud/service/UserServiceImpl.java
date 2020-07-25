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
import com.timgroup.statsd.StatsDClient;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;


@Service
public class UserServiceImpl implements UserDetailsService {

	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private StatsDClient stats;
	
	private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Transactional
	public User save(User user) {
		// TODO Auto-generated method stub
		
		if(!userDao.existsById(user.getUserName())) {
			String userPassword = user.getUserPassword();
			String decode = new String(Base64.getDecoder().decode(userPassword));
			String password = BCrypt.hashpw(decode,BCrypt.gensalt());
			user.setUserPassword(password);
			long timeStarted = System.currentTimeMillis();
			User returnUser = userDao.save(user);
			long timeEnded = System.currentTimeMillis();
            long diffTime = (timeEnded - timeStarted);
            stats.recordExecutionTime("Added user in ",diffTime);
			logger.info("user create successfull");
			return returnUser;
		}else {
			logger.error("user create error");
			return null;
		}

	}
	
	public User update(User user) {
		long timeStarted = System.currentTimeMillis();
		User userUpdate = userDao.findById(user.getUserName()).get();
		long timeEnded = System.currentTimeMillis();
        long diffTime = (timeEnded - timeStarted);
        stats.recordExecutionTime("Update user in ",diffTime);
		System.out.println(userUpdate);
		userUpdate.setFirstName(user.getFirstName());
		userUpdate.setLastName(user.getLastName());
		long timeStart = System.currentTimeMillis();
		User user1 = userDao.save(userUpdate);
		System.out.println(user1);
		logger.info("user update successful");
		return user1;
		
	}
	
	public User getUser(User user) {
		long timeStarted = System.currentTimeMillis();
		User userDetails = userDao.findById(user.getUserName()).get();
		long timeEnded = System.currentTimeMillis();
        long diffTime = (timeEnded - timeStarted);
        stats.recordExecutionTime("get User in ",diffTime);
		logger.info("get User success");
		return userDetails;
	}
	
	public User userLogin(User user) {
		long timeStarted = System.currentTimeMillis();
		User userlogin = userDao.findById(user.getUserName()).get();
		long timeEnded = System.currentTimeMillis();
        long diffTime = (timeEnded - timeStarted);
        stats.recordExecutionTime("get UserName in ",diffTime);
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
		long timeStarted = System.currentTimeMillis();
        User user = userDao.findByUsername(username);
        long timeEnded = System.currentTimeMillis();
        long diffTime = (timeEnded - timeStarted);
        stats.recordExecutionTime("get UserName in ",diffTime);
        if (user == null) {
        	logger.error("User not found");
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getUserPassword(),
                new ArrayList<>());
    }
	
	public User updatePassword(Password password) {
		long timeStarted = System.currentTimeMillis();
		User user = userDao.findByUsername(password.getUserName());
		long timeEnded = System.currentTimeMillis();
        long diffTime = (timeEnded - timeStarted);
        stats.recordExecutionTime("get UserName in ",diffTime);
		String oldPassword = password.getOldPassword();
		String oldDbPassword = user.getUserPassword();
		String decode = new String(Base64.getDecoder().decode(oldPassword));
		boolean matchPassword = BCrypt.checkpw(decode, oldDbPassword);
		
		if(matchPassword==true) {
			String newPassword = password.getNewPassword();
			String decodeNew = new String(Base64.getDecoder().decode(newPassword));
			String finalPassword = BCrypt.hashpw(decodeNew,BCrypt.gensalt());
			user.setUserPassword(finalPassword);
			long timeStart = System.currentTimeMillis();
			User finalUser = userDao.save(user);
			long timeEnd = System.currentTimeMillis();
	        long diff = (timeEnded - timeStarted);
	        stats.recordExecutionTime("Password Updated in ",diff);
			logger.info("password match in update");
			return finalUser;
			
		}
		logger.error("passwords dont match in update");
		return null;
	}

	public User getUser(String user) {
		User use = userDao.findByUsername(user);
		if(use != null) {
			return use;
		}
		else {
			return null;
		}
	}
	

}

	
