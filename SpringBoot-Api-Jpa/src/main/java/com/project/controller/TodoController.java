package com.project.controller;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.project.model.Todo;
import com.project.service.TodoService;

@RestController
@RequestMapping("api/todos")
public class TodoController {

	private final TodoService todoService;

	public TodoController(TodoService todoService) {
		super();
		this.todoService = todoService;
	}
	
	@RequestMapping(method = { RequestMethod.POST, RequestMethod.PUT })
    public ResponseEntity<?> saveOrUpdate(@RequestBody Todo todo) {
        Todo todoInstance = todoService.saveOrUpdate(todo);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(todoInstance.getId()).toUri();
        return ResponseEntity.created(location).build();
    }
	

	
	@PatchMapping("/{id}")
	public ResponseEntity<Todo> updateCompleted(@PathVariable Long id){
	    Todo todo = todoService.update(id);
	    URI location = ServletUriComponentsBuilder
	    		.fromCurrentRequest()
	            .path("/{id}")
	            .buildAndExpand(todo.getId()).toUri();
	    return ResponseEntity.ok().header("Location", location.toString()).body(todo);
	}
	
	
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Todo> delete(@PathVariable Long id){
		todoService.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Todo> getById(@PathVariable Long id){
		return ResponseEntity.ok(todoService.findById(id));
	}
	
	@GetMapping()
	public ResponseEntity<Iterable<Todo>> getAll() {
		return ResponseEntity.ok(todoService.findAll());
	}
	
}
