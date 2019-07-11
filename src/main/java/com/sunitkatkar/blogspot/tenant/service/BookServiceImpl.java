package com.sunitkatkar.blogspot.tenant.service;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sunitkatkar.blogspot.tenant.model.Book;
import com.sunitkatkar.blogspot.tenant.repository.BookRepository;

@Service
public class BookServiceImpl implements BookService{
	
	private static final Logger LOG = LoggerFactory
            .getLogger(BookServiceImpl.class);

	@Autowired
	private BookRepository bookRepository;
	
	@Override	
	public List<Book> findAll() {
		return bookRepository.findAll();
	}

}
