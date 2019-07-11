package com.sunitkatkar.blogspot.tenant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sunitkatkar.blogspot.tenant.model.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

}
