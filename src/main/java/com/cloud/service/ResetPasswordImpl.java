package com.cloud.service;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cloud.dao.PasswordResetDAO;



@Service
public class ResetPasswordImpl {
	
	@Autowired
	private PasswordResetDAO passwordResetDAO;

	private static final Logger logger = LoggerFactory.getLogger(ResetPasswordImpl.class);

	public ResponseEntity<Object> sendResetEmail(String email) {
		logger.info("Sending reset email:::" + email);
		if (!validateEmail(email)) {
			return new ResponseEntity<Object>(null, HttpStatus.OK);
		} else {
			passwordResetDAO.sendEmailToUser(email);
			logger.info("Email successfully sent");
			return new ResponseEntity<Object>(1, HttpStatus.OK);
		}

	}

	public Boolean validateEmail(String email) {
		if (email != null || (!email.equalsIgnoreCase(""))) {
			String emailvalidator = "^[a-zA-Z0-9_+&-]+(?:\\." + "[a-zA-Z0-9_+&-]+)*@" + "(?:[a-zA-Z0-9-]+\\.)+[a-z"
					+ "A-Z]{2,7}$";

			return email.matches(emailvalidator);
		} else {
			return Boolean.FALSE;
		}

	}

}
