package com.cloud.model;

public class GetBookImage {
	
	String name;
	String image;
	
	public GetBookImage() {
		
	}
	
	public GetBookImage(String name, String image) {
		this.name = name;
		this.image = image;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String key) {
		this.name = key;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	
	

}
