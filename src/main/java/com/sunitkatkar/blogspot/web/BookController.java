package com.sunitkatkar.blogspot.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.sunitkatkar.blogspot.tenant.service.BookService;

@Controller
public class BookController {
	
	@Autowired
	private BookService bookService;
	
	@GetMapping("/all")
	public String showAll(Model model) {
	    model.addAttribute("books", bookService.findAll());
	    return "books/allBooks";
	}

}
