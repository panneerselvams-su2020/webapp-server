package com.cloud.model;



import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.cloud.model.Book;




@Entity
@Table(name="bookimage") 
public class BookImage{
	
	/**
	 * 
	 */

	@Id
	@Column(length = 64)
	private String name;
	
	@JsonBackReference
	@ManyToOne(cascade = {CascadeType.ALL})
	@JoinColumn(name="bookId")
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	private Book book;
	

	
	public BookImage(String name, Book book) {
		this.name = name;
		this.book = book;
	}

	public BookImage() {
		
	}
	

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	

}
