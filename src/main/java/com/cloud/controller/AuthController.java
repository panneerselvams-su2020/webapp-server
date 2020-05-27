package com.cloud.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import com.cloud.configuration.JwtToken;
import com.cloud.model.JwtRequest;
import com.cloud.model.JwtResponse;
import com.cloud.model.User;
import com.cloud.model.UserToken;
import com.cloud.service.UserServiceImpl;

import org.springframework.security.authentication.AuthenticationManager;

@RestController
@CrossOrigin
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtToken jwtToken;

    @Autowired
    private UserServiceImpl jwtUserDetailsService;


    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {


        try{
        	
        	User us = authenticate(new User(authenticationRequest.getUsername(), authenticationRequest.getPassword()));
        

        final UserDetails userDetails = jwtUserDetailsService

                .loadUserByUsername(authenticationRequest.getUsername());

        final String token = jwtToken.generateToken(userDetails);

        return ResponseEntity.ok(new UserToken(us,new JwtResponse(token)));
        }
        catch(Exception e) {
        	
        	return null;
        }

    }

    private User authenticate(User user) throws Exception {

        try {

          User us = jwtUserDetailsService.userLogin(user);
          return us;

        } catch (DisabledException e) {

            throw new Exception("USER_DISABLED", e);

        } catch (BadCredentialsException e) {

            throw new Exception("INVALID_CREDENTIALS", e);

        }
        
       

    }

}