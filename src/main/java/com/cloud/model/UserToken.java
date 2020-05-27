package com.cloud.model;
public class UserToken {
	
	private User user;
	
	private JwtResponse jwt;

	public UserToken(User user, JwtResponse jwt) {
		this.jwt = jwt;
		this.user = user;
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public JwtResponse getJwt() {
		return jwt;
	}

	public void setJwt(JwtResponse jwt) {
		this.jwt = jwt;
	}
	
	

}