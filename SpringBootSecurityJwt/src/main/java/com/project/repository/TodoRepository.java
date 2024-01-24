package com.project.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.model.Todo;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long>{

	 Optional<Todo> findFirstByIdAndStatus(Long id, String status);
	 Optional<Todo> findFirstById(Long id);
}
