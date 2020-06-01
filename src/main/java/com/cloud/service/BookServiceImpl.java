package com.cloud.service;

import java.util.List;
import java.time.LocalDateTime;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.cloud.dao.BookDao;
import com.cloud.model.Book;
import com.cloud.model.Cart;
import com.cloud.model.User;

@Service
public class BookServiceImpl {

	
	@Autowired
	private Cart cart;
	
	@Autowired
	private BookDao bookDao;
	
	
	public Book save(Book book) {

		
		try {
		String isbn = book.getIsbn();
		String userName = book.getUserName();
		
		
		
		Book returnBook = bookDao.findExistingBook(userName, isbn);
		
		if(returnBook!=null) {
		
			LocalDateTime time = LocalDateTime.now();
			book.setCreatedTime(time);
			Book books = bookDao.save(book);
			return books;
		}else {
			return null;
		}
		
	}catch(Exception e) {
		return null;
	}
		
	}
	
	public Book updateBook(Book book) {
		try {
			String isbn = book.getIsbn();
			String userName = book.getUserName();
			
			Book returnBook = bookDao.findExistingBook(userName, isbn);
			
			if(returnBook!=null) {
				LocalDateTime time = LocalDateTime.now();
				returnBook.setAuthor(book.getAuthor());
				returnBook.setBookQuantity(book.getBookQuantity());
				returnBook.setIsbn(isbn);
				returnBook.setUserName(userName);
				returnBook.setTitle(book.getTitle());
				returnBook.setPrice(book.getPrice());
				returnBook.setPubDate(book.getPubDate());				
				Book books = bookDao.save(returnBook);
				return books;
			}else {
				return null;
			}
			
		}catch(Exception e) {
			return null;
		}
	}
	
	public Book deleteBook(Book book) {
		try {
			String isbn = book.getIsbn();
			String userName = book.getUserName();
			Book returnBook = bookDao.findExistingBook(userName, isbn);
			if(returnBook!=null) {
			returnBook.setIsDeleted(true);
			returnBook.setUpdatedTime(LocalDateTime.now());
			Book books = bookDao.save(returnBook);
			return books;
			}else {
				return null;
			}
		}catch(Exception e) {
			return null;
		}
	}
	
	public List<Book> getBooksForSeller(String userName) {
		try {
			boolean checkStatus = false;
			List<Book> returnBook = bookDao.getBooksForSeller(userName, checkStatus);
			return returnBook;
		}catch(Exception e) {
			return null;
		}
	}
	
	public List<Book> getBooksForBuyer(String userName) {
		try {
			boolean checkStatus = false;
			List<Book> returnBook = bookDao.getBooksForBuyer(userName, checkStatus);
			return returnBook;
		}catch(Exception e) {
			return null;
		}
	}
	
	
}
