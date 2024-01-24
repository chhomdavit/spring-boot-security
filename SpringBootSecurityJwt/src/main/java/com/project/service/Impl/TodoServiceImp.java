package com.project.service.Impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.project.dto.TodoBatchRequest;
import com.project.dto.TodoBatchResponse;
import com.project.dto.TodoRequest;
import com.project.dto.TodoResponse;
import com.project.model.Todo;
import com.project.repository.TodoRepository;
import com.project.service.TodoService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Service
@Slf4j
public class TodoServiceImp implements TodoService{
	
	
	private final TodoRepository todoRepository;
    private final ModelMapper modelMapper;
	
	
	@Override
	public TodoResponse create(TodoRequest todoRequest) {
		// TODO Auto-generated method stub
		Todo todo = new Todo();
        todo.setName(todoRequest.name());
        todo.setDescription(todoRequest.description());
        todo.setStatus("ACT");
        todo = todoRepository.save(todo);
        return new TodoResponse(todo.getId(), todo.getName(), todo.getDescription());
	}

	@Override
	public void batchCreate(List<TodoBatchRequest> todoBatchRequests) {
		// TODO Auto-generated method stub
		if(!todoBatchRequests.isEmpty()) {
            List<Todo> todos = new ArrayList<>();
            for(TodoBatchRequest req : todoBatchRequests) {
                todos.add(modelMapper.map(req, Todo.class));
            }
            todoRepository.saveAll(todos);
        }
		
	}

	@Override
	public TodoResponse update(TodoRequest todoRequest, long id) {
		// TODO Auto-generated method stub
		Optional<Todo> todoOptional = todoRepository.findFirstByIdAndStatus(id, "ACT");
        if (todoOptional.isPresent()) {
            todoOptional.get().setId(id);
            todoOptional.get().setName(todoRequest.name());
            todoOptional.get().setDescription(todoRequest.description());
            Todo todo = todoRepository.saveAndFlush(todoOptional.get());
            return new TodoResponse(todo.getId(), todo.getName(), todo.getDescription());
        }
        return null;
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		 Optional<Todo> todo = todoRepository.findFirstByIdAndStatus(id, "ACT");
	        if (todo.isPresent()) {
	            todo.get().setStatus("DEL");
	            todoRepository.save(todo.get());
	     }
	}

	@Override
	public TodoResponse get(Long id) {
		// TODO Auto-generated method stub
		return todoRepository.findFirstById(id)
	           .map(todo -> new TodoResponse(todo.getId(), todo.getName(), todo.getDescription()))
	           .orElse(null);
	}

	@Override
	public List<TodoBatchResponse> getAll(Integer page, Integer size) {
		// TODO Auto-generated method stub
        if(page == null || size == null) {
            page = 0;
            size = 10;
        }
        Pageable pageable = PageRequest.of(page, size);
        Page<Todo> todos = todoRepository.findAll(pageable);
        if(todos.getContent().isEmpty()) {
            log.info("No data found");
            return new ArrayList<>();
        }

        List<TodoBatchResponse> todoResponses = new ArrayList<>();
        for(Todo todo : todos.getContent()) {

            todoResponses.add(modelMapper.map(todo, TodoBatchResponse.class));
        }
        return todoResponses;
    }
	

}
