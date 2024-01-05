package com.project.service;

import com.project.model.Todo;

public interface TodoService {

	Todo saveOrUpdate(Todo todo);
	
	Todo update(Long id);
	
	void delete(Long id);
	
	Todo findById(Long id);
	
	Iterable<Todo> findAll();
}
