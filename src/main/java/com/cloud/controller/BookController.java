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
import com.timgroup.statsd.StatsDClient;

@RestController
public class BookController {

	@Autowired
	private BookServiceImpl bookservice;
	
	@Autowired
	private BookDao bookDao;
	
	@Autowired
	private StatsDClient stats;
		

	private final Logger logger = LoggerFactory.getLogger(BookController.class);

@PostMapping("/addBook")
	public ResponseEntity<Book> save(@RequestBody BookRequest request) {
	
		stats.incrementCounter("endpoint.book.bookSave.http.post");
		long statsStart = System.currentTimeMillis();
		Book book = bookservice.save(request.getBook());	
		
		Set<BookImage> ImgSet = new HashSet<BookImage>();
		
		if(book!=null) {
			BookImage img = new BookImage();
			img.setBook(book);
			List<String> url = request.getImage();
			List<BookImage> images = bookservice.uploadImage(img,url);
			if(images.size()>0) {
				long statsEnd = System.currentTimeMillis();
				long duration = (statsEnd - statsStart);
				stats.recordExecutionTime("SaveBookApiCall",duration);
				return ResponseEntity.ok(book);
			}else {
				long statsEnd = System.currentTimeMillis();
				long duration = (statsEnd - statsStart);
				stats.recordExecutionTime("SaveBookApiCall",duration);
				return ResponseEntity.ok(book);
			}
			
		}else {
			long statsEnd = System.currentTimeMillis();
			long duration = (statsEnd - statsStart);
			stats.recordExecutionTime("SaveBookApiCall",duration);
			return ResponseEntity.ok(null);
		}
		
		
	}
	
	@PutMapping("/updateBook")
	public ResponseEntity<Book> updateBook(@RequestBody Book bookObj){
		stats.incrementCounter("endpoint.book.updateBook.http.put");
		long statsStart = System.currentTimeMillis();
		Book book = bookservice.updateBook(bookObj);
		long statsEnd = System.currentTimeMillis();
		long duration = (statsEnd - statsStart);
		stats.recordExecutionTime("UpdateBookApiCall",duration);
		
		return ResponseEntity.ok(book);
	}
	
	@PutMapping("/deleteBook")
	public ResponseEntity<Book> deleteBook(@RequestBody Book bookObj){
		stats.incrementCounter("endpoint.book.deleteBook.http.put");
		long statsStart = System.currentTimeMillis();
		Book book = bookservice.deleteBook(bookObj);
		long statsEnd = System.currentTimeMillis();
		long duration = (statsEnd - statsStart);
		stats.recordExecutionTime("DeleteBookApiCall",duration);
		return ResponseEntity.ok(book);
	}
	
	@GetMapping("/getSellerBooks")
	public ResponseEntity<List<Book>> getBooksForSeller(Authentication auth){
		stats.incrementCounter("endpoint.book.getBookForSeller.http.get");
		long statsStart = System.currentTimeMillis();
		List<Book> book = bookservice.getBooksForSeller(auth.getName());
		long statsEnd = System.currentTimeMillis();
		long duration = (statsEnd - statsStart);
		stats.recordExecutionTime("GetBookForSellerApiCall",duration);
		return ResponseEntity.ok(book);
	}
	
	@GetMapping("/getBuyerBooks")
	public ResponseEntity<List<Book>> getBooksForBuyer(Authentication auth){
		stats.incrementCounter("endpoint.book.getBookForBuyer.http.get");
		long statsStart = System.currentTimeMillis();
		List<Book> book = bookservice.getBooksForBuyer(auth.getName());
		long statsEnd = System.currentTimeMillis();
		long duration = (statsEnd - statsStart);
		stats.recordExecutionTime("GetBookForBuyerApiCall",duration);
		return ResponseEntity.ok(book);
	}
	
	@PostMapping(path="/viewImage")
	public ResponseEntity<List<GetBookImage>> view(@RequestBody Book book, Authentication auth){
		stats.incrementCounter("endpoint.book.viewImage.http.post");
		long statsStart = System.currentTimeMillis();
		String userName = auth.getName();
		List<GetBookImage> image = bookservice.viewBooks(book,userName);
		long statsEnd = System.currentTimeMillis();
		long duration = (statsEnd - statsStart);
		stats.recordExecutionTime("ViewImageApiCall",duration);
		return ResponseEntity.ok(image);
		
	}
	
	@PostMapping(path="/viewBuyerImage")
	public ResponseEntity<List<GetBookImage>> buyview(@RequestBody Book book,Authentication auth){
		String uniqueId = book.getIsbn()+"_"+auth.getName();
		stats.incrementCounter(uniqueId);
		stats.incrementCounter("endpoint.book.viewImageForBuyer.http.post");
		stats.incrementCounter("");
		long statsStart = System.currentTimeMillis();
		String userName = auth.getName();
		List<GetBookImage> bk = bookservice.getBookForBuyer(book, userName);
		long statsEnd = System.currentTimeMillis();
		long duration = (statsEnd - statsStart);
		stats.recordExecutionTime("ViewImageForBuyerApiCall",duration);
		return ResponseEntity.ok(bk);
		
	}
	
	@PutMapping(path="/deleteimg")
    public ResponseEntity<BookImage> delete(@RequestBody BookImage book){
		stats.incrementCounter("endpoint.book.deleteImage.http.put");
		long statsStart = System.currentTimeMillis();
		BookImage image = bookservice.removeBook(book);
		long statsEnd = System.currentTimeMillis();
		long duration = (statsEnd - statsStart);
		stats.recordExecutionTime("DeleteImageApiCall",duration);
		return ResponseEntity.ok(image);
		
	}
	
	@PutMapping(path="/updateimg")
    public ResponseEntity<List<BookImage>> updateimage(@RequestBody BookRequest book){
	
		stats.incrementCounter("endpoint.book.updateImage.http.put");
		long statsStart = System.currentTimeMillis();
			BookImage image = new BookImage();
			image.setBook(book.getBook());
			List<String> url = book.getImage();
			List<BookImage> img = bookservice.uploadImage(image, url);
			if(img.size() > 0) {
				long statsEnd = System.currentTimeMillis();
				long duration = (statsEnd - statsStart);
				stats.recordExecutionTime("UpdateImageApiCall",duration);
				return ResponseEntity.ok(img);
			}
			else {
				long statsEnd = System.currentTimeMillis();
				long duration = (statsEnd - statsStart);
				stats.recordExecutionTime("UpdateImageApiCall",duration);
				return ResponseEntity.ok(null);
			}
	
 }
	
}
