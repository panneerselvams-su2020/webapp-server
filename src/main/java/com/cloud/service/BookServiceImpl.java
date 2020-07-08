package com.cloud.service;

import java.util.List;
import java.util.Set;

import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import com.cloud.dao.BookDao;
import com.cloud.dao.ImageDao;
import com.cloud.model.Book;
import com.cloud.model.BookImage;
import com.cloud.model.Cart;
import com.cloud.model.GetBookImage;
import com.cloud.model.User;
import com.timgroup.statsd.StatsDClient;

@Service
public class BookServiceImpl {

	@Value("${s3.bucket}")
	private String s3;
		
	@Autowired 
	ImageDao imagedao;
	
	@Autowired
	private AmazonS3 s3client;
	
	@Autowired
	private StatsDClient stats;

	
	@Autowired
	private BookDao bookDao;
	
	private final Logger logger = LoggerFactory.getLogger(BookServiceImpl.class);
	
	
	public Book save(Book book) {

		stats.incrementCounter("endpoint.book.create.http.post");
        long statsStart = System.currentTimeMillis();
		try {
		String isbn = book.getIsbn();
		String userName = book.getUserName();
		
		
		
		Book returnBook = bookDao.findExistingBook(userName, isbn);
		
		if(returnBook==null) {
			long startTime = System.currentTimeMillis();
			Book books = bookDao.save(book);
			long endTime = System.currentTimeMillis();
			long duration = (endTime - startTime);
			logger.info("Book create successfull");
			return books;
		}else if(returnBook.isDeleted()==true){
			long startTime = System.currentTimeMillis();
			Book books = bookDao.save(book);
			long endTime = System.currentTimeMillis();
			long duration = (endTime - startTime);
			logger.info("Book create successfull");
			return books;
		}else {
			logger.error("Book creation failed");
			return null;
		}
		
	}catch(Exception e) {
		logger.error("Error in book creation");
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
				logger.info("Book update successfull");
				return books;
			}else {
				return null;
			}
			
		}catch(Exception e) {
			logger.error("Error in book update");
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
			Book books = bookDao.save(returnBook);
			logger.info("Book deletion successfull");
			return books;
			}else {
				return null;
			}
		}catch(Exception e) {
			logger.error("Error in book deletion");
			return null;
		}
	}
	
	public List<Book> getBooksForSeller(String userName) {
		try {
			boolean checkStatus = false;
			List<Book> returnBook = bookDao.getBooksForSeller(userName, checkStatus);
			logger.info("Book list for seller successfull");
			return returnBook;
		}catch(Exception e) {
			logger.error("Error in booklist seller");
			return null;
		}
	}
	
	public List<Book> getBooksForBuyer(String userName) {
		try {
			boolean checkStatus = false;
			List<Book> returnBook = bookDao.getBooksForBuyer(userName, checkStatus);
			logger.info("Book list for buyer successfull");
			return returnBook;
		}catch(Exception e) {
			logger.error("Error in booklist buyer");
			return null;
		}
	}

	public List<BookImage> uploadImage(BookImage img, List<String> url) {
		// TODO Auto-generated method stub
		Set<BookImage> imageSet = new HashSet<BookImage>();
		List<BookImage> listImage = new ArrayList<BookImage>();
		
		Book book = img.getBook();
		Book bk = bookDao.findExistingBook(book.getUserName(), book.getIsbn());
		
		for(String s : url) {
			long timeStamp = System.currentTimeMillis();
			String imageName = img.getBook().getUserName()+"-time-"+timeStamp;
			img.setName(imageName);
			img.setBook(bk);
			File f = ConvertBlobToImage(s);
			
			try {
				s3client.putObject(new PutObjectRequest(s3,imageName,f));
				BookImage image =  imagedao.save(img);
				imageSet.add(image);
				listImage.add(image);
				logger.info("Book list for buyer successfull");
				
			}catch(AmazonServiceException a) {
				System.out.println("Uploading image in s3 bucket failed");
				logger.error("Error in upload image to s3 bucket");
			}
			
		}
		book.setImages(imageSet);
		bookDao.save(bk);
		return listImage;
	}

	private File ConvertBlobToImage(String s) {
		// TODO Auto-generated method stub
		String base64 = s.split(",")[1];
		byte[] decode = Base64.getDecoder().decode(base64);
		ByteArrayInputStream bis = new ByteArrayInputStream(decode);
		BufferedImage img = null;
		
		try {
			img = ImageIO.read(bis);
		}
		catch(Exception e) {
			
		}
		File file = new File("IncomingFile.png");
		try {
			ImageIO.write(img, "png" , file);
		}
		catch(Exception e)
		{
			logger.error("Error in converting image to blob");
		}
		
		return file;
	}

	public List<GetBookImage> viewBooks(Book book, String userName) {
		// TODO Auto-generated method stub
		try {
			 String isbn = book.getIsbn();
			 Book bk = bookDao.findExistingBook(userName,isbn);
            
			 List<BookImage> image = imagedao.findbybook(bk);
			 List<GetBookImage> obj = new ArrayList<>();
			  
			
			for(BookImage b: image) {
				GetBookImage getBook = new GetBookImage();
				String name = b.getName();
				S3ObjectInputStream is = s3client.getObject(new GetObjectRequest(s3, name)).getObjectContent();
				try {
					byte[] bytes = IOUtils.toByteArray(is);
					StringBuilder sb = new StringBuilder();
					sb.append("data:image/png;base64,");
					sb.append(Base64.getEncoder().encodeToString(bytes));
					getBook.setImage(sb.toString());
					getBook.setName(name);
					
					obj.add(getBook);
					
				}
				catch(IOException e) {
					System.out.println("GetBook failed");
					logger.error("GetBookImage failed");
				}
			}
			return obj;
		}
		catch(Exception e) {
			System.out.println("Seller View Book Exception");
			logger.error("Seller View BookImage Exception");
			return null;
		}
		
		
	}

	public BookImage removeBook(BookImage image) {
		// TODO Auto-generated method stub
	      String name = image.getName();
	      try {
	    	  s3client.deleteObject(s3, name);  
	    	  imagedao.delete(image);
	    	  BookImage img = imagedao.getOne(name);
	    	  logger.info("remove book image successfull");
	    	  return img;
	      }
	      catch(Exception e) {
	    	  logger.error("error in remove image");
	    	  return image;
	      }
	}

	public List<GetBookImage> getBookForBuyer(Book book,String userName) {
		// TODO Auto-generated method stub
		
		try {
			 String isbn = book.getIsbn();
			 
			 List<Book> bk = bookDao.findExistingBookByIsbn(isbn,userName);
			 
			 
            List<GetBookImage> obj = new ArrayList<>();
            for(Book b : bk) {
			 List<BookImage> image = imagedao.findbybook(b);
			 for(BookImage i: image) {
					GetBookImage gi = new GetBookImage();
					String name = i.getName();
					S3ObjectInputStream is = s3client.getObject(new GetObjectRequest(s3, name)).getObjectContent();
					try {
						byte[] bytes = IOUtils.toByteArray(is);
						StringBuilder st = new StringBuilder();
						st.append("data:image/png;base64,");
						st.append(Base64.getEncoder().encodeToString(bytes));
						gi.setImage(st.toString());
						gi.setName(name);
						obj.add(gi);
						logger.info("Get Book for buyer successfull");
					}
					catch(IOException e) {
						System.out.println("getbook().retrieval failed :" + e.getMessage());
						logger.error("error in getBookforbuyer");
					}
				}
				
            }
            return obj;
			
		}
		catch(Exception e) {
			System.out.println("Exception in book view of seller");
			logger.error("Exception in book view of seller");
			return null;
		}
	
	}

	
	
}
