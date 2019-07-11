package com.sunitkatkar.blogspot.tenant.dto;

import java.util.List;

import com.sunitkatkar.blogspot.tenant.model.Book;

public class BooksCreationDto {
	private List<Book> books;
	 
    // default and parameterized constructor
 
    public void addBook(Book book) {
        this.books.add(book);
    }
     
    // getter and setter
}
