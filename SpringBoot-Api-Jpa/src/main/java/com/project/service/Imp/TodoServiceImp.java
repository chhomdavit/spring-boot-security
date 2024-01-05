package com.project.service.Imp;


import java.util.Optional;

import org.springframework.stereotype.Service;

import com.project.excepetion.ResourceNotFoundException;
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
	
	public TodoServiceImp(TodoRepository todoRepository) {
		super();
		this.todoRepository = todoRepository;
	}

	@Override
	public Todo saveOrUpdate(Todo todo) {
		Optional<Todo> todoInstance = todoRepository.findById(todo.getId());
		if(todoInstance.isPresent()) {
			todoInstance.get().setId(todo.getId());
			todoInstance.get().setTitle(todo.getTitle());
			todoInstance.get().setDescription(todo.getDescription());
			todoInstance.get().setCompleted(todo.isCompleted());
			return todoRepository.save(todoInstance.get());
		}
		return todoRepository.save(todo);
	}

	
	@Override
	public Todo update(Long id) {
	    Optional<Todo> todOptional = todoRepository.findById(id);
	    if (todOptional.isPresent()) {
	        Todo todo = todOptional.get();
	        todo.setCompleted(true);
	        todoRepository.save(todo);
	        return todo; 
	    }
	    throw new ResourceNotFoundException(String.format("Update Todo with ID %s not found", id));
	}
	
	@Override
	public void delete(Long id) {
		Optional<Todo> todOptional = todoRepository.findById(id);
		if(todOptional.isPresent()) {
			todoRepository.delete(todOptional.get());
		}
		
	}

	@Override
	public Todo findById(Long id) {
		Optional<Todo> todOptional = todoRepository.findById(id);
		if(todOptional.isPresent()) {
			return todOptional.get();
		}
//		log.warn("Find Todo ID {} not found.", id);
		throw new ResourceNotFoundException(String.format("Todo ID %s not found", id));
	}

	@Override
	public Iterable<Todo> findAll() {
		return todoRepository.findAll();
	}

}
