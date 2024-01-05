package com.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.model.Book;

public interface BookRepository extends JpaRepository<Book, Long>{

}
