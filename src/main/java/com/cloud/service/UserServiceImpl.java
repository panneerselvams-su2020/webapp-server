package com.cloud.service;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

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
	

	@Transactional
	public User save(User user) {
		// TODO Auto-generated method stub
		
		if(!userDao.existsById(user.getUserName())) {
			String userPassword = user.getUserPassword();
			String decode = new String(Base64.getDecoder().decode(userPassword));
			String password = BCrypt.hashpw(decode,BCrypt.gensalt());
			user.setUserPassword(password);
			User returnUser = userDao.save(user);
			return returnUser;
		}else {
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
		return user1;
		
	}
	
	public User userLogin(User user) {
		System.out.println("inside login");
		User userlogin = userDao.findById(user.getUserName()).get();
		
		String enteredPassword = user.getUserPassword();
		String decode = new String(Base64.getDecoder().decode(enteredPassword));
		boolean matchPassword = BCrypt.checkpw(decode, userlogin.getUserPassword());
		
		if(matchPassword) {
			System.out.println("pwd matchws");
			System.out.println(userlogin.toString());
			return userlogin;
		}else {
			System.out.println("unmatched");
			return null;
		}
		
		
}
	
	@Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userDao.findByUsername(username);
        if (user == null) {
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
			return finalUser;
			
		}
		return null;
	}
	

}

	
