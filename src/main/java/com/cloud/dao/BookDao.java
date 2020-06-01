package com.cloud.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.cloud.model.Book;

@Repository
public interface BookDao extends JpaRepository<Book, Integer>{
	
	@Query("select book from Book book where book.userName=?1 and book.isbn=?2")
	Book findExistingBook(String userName, String isbn);
	
	@Query("select books from Book where book.userName=?1 and book.isDeleted=?2")
	List<Book> getBooksForSeller(String userName, boolean isDeleted);
	
	@Query("select books from Book where book.userName!=?1 and book.isDeleted=?2")
	List<Book> getBooksForBuyer(String userName, boolean isDeleted);
	
}
