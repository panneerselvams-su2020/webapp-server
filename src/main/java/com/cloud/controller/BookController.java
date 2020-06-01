package com.cloud.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cloud.dao.BookDao;
import com.cloud.model.Book;
import com.cloud.model.User;
import com.cloud.service.BookServiceImpl;

@RestController
public class BookController {

	@Autowired
	private BookServiceImpl bookservice;
	
	@Autowired
	private BookDao bookDao;
	
	@PostMapping("/addBook")
	public ResponseEntity<Book> save(@RequestBody Book bookObj) {
	
		Book book = bookservice.save(bookObj);
		
		return ResponseEntity.ok(book);
}
	
	@PutMapping("/updateBook")
	public ResponseEntity<Book> updateBook(@RequestBody Book bookObj){
		Book book = bookservice.save(bookObj);
		
		return ResponseEntity.ok(book);
	}
	
	@PutMapping("/deleteBook")
	public ResponseEntity<Book> deleteBook(@RequestBody Book bookObj){
		Book book = bookservice.save(bookObj);
		
		return ResponseEntity.ok(book);
	}
	
	@GetMapping("getSellerBooks")
	public ResponseEntity<List<Book>> getBooksForSeller(@RequestBody Book bookObj){
		List<Book> book = bookservice.getBooksForSeller(bookObj.getUserName());
		return ResponseEntity.ok(book);
	}
	
	@GetMapping("getBuyerBooks")
	public ResponseEntity<List<Book>> getBooksForBuyer(@RequestBody Book bookObj){
		List<Book> book = bookservice.getBooksForBuyer(bookObj.getUserName());
		return ResponseEntity.ok(book);
	}
}
