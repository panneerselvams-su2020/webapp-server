package com.cloud.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import javax.persistence.Id;

@Entity
@Table(name="userDetails")
public class User{

	
	@Id
	@Column(name = "userName", unique= true, nullable=false, length=64)
	private String username;
	
	@Column(name="userPassword", nullable=false,length=64)
	private String userPassword;
	
	@Column(name = "firstName", nullable = false,length=64)
	private String firstName;
	
	@Column(name="lastName", nullable=false,length=64)
	private String lastName;

	public User(){
		
	}
	
	public User(String userName, String userPassword,String firstName, String lastName) {
		this.username = userName;
		this.userPassword = userPassword;
		this.firstName=firstName;
		this.lastName=lastName;
		
	}
	
	public User(String userName, String userPassword) {
		this.username = userName;
		this.userPassword = userPassword;
		
	}

	public String getUserName() {
		return username;
	}

	public void setUserName(String userName) {
		this.username = userName;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	@Override
	public String toString() {
		return "User[firstName = " +firstName+ " ,lastName = " +lastName+ " userName = " +username+ "  userPassword : " + userPassword + "]";
	}
	
}