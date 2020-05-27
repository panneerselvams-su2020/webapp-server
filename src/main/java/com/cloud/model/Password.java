package com.cloud.model;

public class Password {
	private String oldPassword;
	private String newPassword;
	private String userName;
	
	public String getOldPassword() {
		return oldPassword;
	}
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public Password() {
		
	}
	
	public Password(String oldPassword, String newPassword, String userName) {
		this.oldPassword=oldPassword;
		this.newPassword=newPassword;
		this.userName=userName;
	}
}
