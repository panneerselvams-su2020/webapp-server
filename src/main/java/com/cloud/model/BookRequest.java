package com.cloud.model;

import java.util.List;

public class BookRequest {

	List<String> image;
	 
	Book book;
	
	public BookRequest(){}
	
	public BookRequest(Book book, List<String> image) {

		this.book = book;
		this.image = image;
	}
	
	
	public List<String> getImage() {
		return image;
	}
	public void setImage(List<String> image) {
		this.image = image;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}
	
	
}
