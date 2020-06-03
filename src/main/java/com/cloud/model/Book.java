package com.cloud.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;


@Entity
@Table(name="Book")
public class Book{

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "bookId", unique= true, nullable=false)
	private int bookId;
	
	@Column(name="isbn",nullable=false)
	private String isbn;
	
	@Column(name = "title", nullable = false)
	private String title;
	
	@Column(name="author", nullable=false)
	private String author;
	
	@Column(name="pubDate", nullable=false)
	private LocalDate pubDate;

	
	@Column(name="bookQuantity", nullable=false)
	@Min(value=0)
	@Max(value=999)
	private int bookQuantity;
	
	@Column(name="price",nullable=false)
	@DecimalMin("0.01")
	@DecimalMax("9999.99")
	private double price;
	
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "createdTime")
	private Date createdTime;

	@UpdateTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updatedTime")
	private Date updatedTime;
	
	@Column(name="userName")
	private String userName;
	
	@Column(name="isDeleted", nullable=false)
	private boolean isDeleted =false;
	
	public Book() {
	}
	
	public Book(String isbn, String title, String author, LocalDate pubDate, int bookQuantity,double price, Date createdTime, Date updatedTime, boolean isDeleted) {
		this.isbn=isbn;
		this.title=title;
		this.author=author;
		this.pubDate=pubDate;
		this.bookQuantity=bookQuantity;
		this.price=price;
		this.createdTime=createdTime;
		this.updatedTime=updatedTime;
		this.isDeleted=isDeleted;
	}
	

	public boolean isDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public int getBookId() {
		return bookId;
	}

	public void setBookId(int bookId) {
		this.bookId = bookId;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public LocalDate getPubDate() {
		return pubDate;
	}

	public void setPubDate(LocalDate pubDate) {
		this.pubDate = pubDate;
	}

	public int getBookQuantity() {
		return bookQuantity;
	}

	public void setBookQuantity(int bookQuantity) {
		this.bookQuantity = bookQuantity;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public Date getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(Date updatedTime) {
		this.updatedTime = updatedTime;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	
	

	
	@Override
	public String toString() {
		return "Book[isbn = " +isbn+ " ,author = " +author+ " pubDate = " +pubDate+ "  bookQuantity : " + bookQuantity + " price : "+price+ "createdTime : " +createdTime+ "updatedTime : " +updatedTime+ "userName : " +userName+ "isDeleted = "+isDeleted+ "]";
	}
}
