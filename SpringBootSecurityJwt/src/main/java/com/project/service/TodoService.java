package com.project.service;

import java.util.List;

import com.project.dto.TodoBatchRequest;
import com.project.dto.TodoBatchResponse;
import com.project.dto.TodoRequest;
import com.project.dto.TodoResponse;

public interface TodoService {

	TodoResponse create(TodoRequest todoRequest);
	
    void batchCreate(List<TodoBatchRequest> batchRequests);
    
    TodoResponse update(TodoRequest todoRequest, long id);
    
    void delete(Long id);
    
    TodoResponse get(Long id);
    
    List<TodoBatchResponse> getAll(Integer page, Integer size);
}
