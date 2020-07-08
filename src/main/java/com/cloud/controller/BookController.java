package com.cloud.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cloud.dao.BookDao;
import com.cloud.model.Book;
import com.cloud.model.BookImage;
import com.cloud.model.BookRequest;
import com.cloud.model.GetBookImage;
import com.cloud.model.User;
import com.cloud.service.BookServiceImpl;

@RestController
public class BookController {

	@Autowired
	private BookServiceImpl bookservice;
	
	@Autowired
	private BookDao bookDao;
		

	private final Logger logger = LoggerFactory.getLogger(BookController.class);

@PostMapping("/addBook")
	public ResponseEntity<List<BookImage>> save(@RequestBody BookRequest request) {
	
		Book book = bookservice.save(request.getBook());	
		
		Set<BookImage> ImgSet = new HashSet<BookImage>();
		
		if(book!=null) {
			BookImage img = new BookImage();
			img.setBook(book);
			List<String> url = request.getImage();
			List<BookImage> images = bookservice.uploadImage(img,url);
			if(images.size()>0) {
				return ResponseEntity.ok(images);
			}else {
				return ResponseEntity.ok(images);
			}
			
		}else {
			return ResponseEntity.ok(null);
		}
		
		
	}
	
	@PutMapping("/updateBook")
	public ResponseEntity<Book> updateBook(@RequestBody Book bookObj){
		Book book = bookservice.updateBook(bookObj);
		
		return ResponseEntity.ok(book);
	}
	
	@PutMapping("/deleteBook")
	public ResponseEntity<Book> deleteBook(@RequestBody Book bookObj){
		Book book = bookservice.deleteBook(bookObj);
		
		return ResponseEntity.ok(book);
	}
	
	@GetMapping("/getSellerBooks")
	public ResponseEntity<List<Book>> getBooksForSeller(Authentication auth){
		List<Book> book = bookservice.getBooksForSeller(auth.getName());
		return ResponseEntity.ok(book);
	}
	
	@GetMapping("/getBuyerBooks")
	public ResponseEntity<List<Book>> getBooksForBuyer(Authentication auth){
		List<Book> book = bookservice.getBooksForBuyer(auth.getName());
		return ResponseEntity.ok(book);
	}
	
	@PostMapping(path="/viewImage")
	public ResponseEntity<List<GetBookImage>> view(@RequestBody Book book, Authentication auth){
		String userName = auth.getName();
		List<GetBookImage> image = bookservice.viewBooks(book,userName);
		return ResponseEntity.ok(image);
		
	}
	
	@PostMapping(path="/viewBuyerImage")
	public ResponseEntity<List<GetBookImage>> buyview(@RequestBody Book book,Authentication auth){
		String userName = auth.getName();
		List<GetBookImage> bk = bookservice.getBookForBuyer(book, userName);
		return ResponseEntity.ok(bk);
		
	}
	
	@PutMapping(path="/deleteimg")
    public ResponseEntity<BookImage> delete(@RequestBody BookImage book){
		
		BookImage image = bookservice.removeBook(book);
		return ResponseEntity.ok(image);
		
	}
	
	@PutMapping(path="/updateimg")
    public ResponseEntity<List<BookImage>> updateimage(@RequestBody BookRequest book){
	
		    
			BookImage image = new BookImage();
			image.setBook(book.getBook());
			List<String> url = book.getImage();
			List<BookImage> img = bookservice.uploadImage(image, url);
			if(img.size() > 0)
				return ResponseEntity.ok(img);
			else
				return ResponseEntity.ok(null);
	
 }
	
}
