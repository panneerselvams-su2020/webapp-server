package com.cloud.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="Cart")
public class Cart {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cartId", unique= true, nullable=false)
	private int cartId;
	
	@Column(name="userName",nullable=false)
	private String userName;
	
	@ManyToOne
	@JoinColumn(name="bookId")
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	private Book book;
	
	
	@Column(name="cartQuantity", nullable=false)
	private int cartQuantity;
	
	@Column(name="cartStatus", nullable=false)
	private boolean cartStatus= false;

	public int getCartId() {
		return cartId;
	}

	public void setCartId(int cartId) {
		this.cartId = cartId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public int getCartQuantity() {
		return cartQuantity;
	}

	public void setCartQuantity(int cartQuantity) {
		this.cartQuantity = cartQuantity;
	}

	public boolean getCartStatus() {
		return cartStatus;
	}

	public void setCartStatus(boolean cartStatus) {
		this.cartStatus = cartStatus;
	}
	
	public Cart() {
		
	}
	
	@Override
	public String toString() {
		return "Cart[cartId = " +cartId+ " ,userName = " +userName+ " bookId = " +book.getBookId()+ "  cartQuantity : " + cartQuantity + " cartStatus = " +cartStatus+ "]";
	}

}
