package com.cloud.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cloud.model.Book;
import com.cloud.model.BookImage;


public interface ImageDao extends JpaRepository<BookImage, String>{

	
	@Query("Select image from BookImage image where image.book = ?1")
	List<BookImage> findbybook(Book book);

}
