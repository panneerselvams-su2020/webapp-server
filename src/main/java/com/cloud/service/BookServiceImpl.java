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

@Service
public class BookServiceImpl {

	@Value("${s3.bucket}")
	private String s3;
		
	@Autowired 
	ImageDao imagedao;
	
	@Autowired
	private AmazonS3 s3client;

	
	@Autowired
	private BookDao bookDao;
	
	
	public Book save(Book book) {

		
		try {
		String isbn = book.getIsbn();
		String userName = book.getUserName();
		
		
		
		Book returnBook = bookDao.findExistingBook(userName, isbn);
		
		if(returnBook==null) {
			Book books = bookDao.save(book);
			return books;
		}else if(returnBook.isDeleted()==true){
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
				
			}catch(AmazonServiceException a) {
				System.out.println("Uploading image in s3 bucket failed");
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
				}
			}
			return obj;
		}
		catch(Exception e) {
			System.out.println("Seller View Book Exception");
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
	    	  return img;
	      }
	      catch(Exception e) {
	    	  return image;
	      }
	}
	
	
}
