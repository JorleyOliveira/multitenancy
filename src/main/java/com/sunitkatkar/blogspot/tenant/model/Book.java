package com.sunitkatkar.blogspot.tenant.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "book")
public class Book {
	
    @Id        
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_generator")
	@SequenceGenerator(name="book_generator", sequenceName = "book_seq", initialValue = 1, allocationSize = 1)    
    @Column(name = "book_id")    
	private long id;
 
    @Column(name = "title")
    private String title;
 
    @Column(name = "author")
    private String author;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
     
    // getters and setters
    
}