package com.cloud.server;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;

import org.springframework.http.ResponseEntity;

import com.cloud.controller.UserController;
import com.cloud.model.User;

public class AuthenticationTest {
	@Test
    public void authenticateLoginTest() {
    	UserController userController = new UserController();
        User user = new User();
        user.setUserName("sridharprasad.p@gmail.com");
        user.setUserPassword("12345678");
        ResponseEntity<String> responseEntity = userController.authenticateLogin(user);
        assertEquals(200, responseEntity.getStatusCodeValue());
    }
}

