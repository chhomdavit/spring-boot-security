package com.project.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.dto.TodoBatch;
import com.project.dto.TodoRequest;
import com.project.dto.TodoResponse;
import com.project.service.TodoService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(
		value = "/api/v1/auth",
		consumes = MediaType.APPLICATION_JSON_VALUE,
	    produces = MediaType.APPLICATION_JSON_VALUE
		)
@CrossOrigin(origins = "http://localhost:1001")
@Slf4j
public class TodoController {

	private final TodoService todoService;

	public TodoController(TodoService todoService) {
		super();
		this.todoService = todoService;
	}
	
	
	
	@PreAuthorize("hasAuthority('USER')")
	@PostMapping("/todos")
    public ResponseEntity<?> create(@RequestBody TodoRequest todoRequest) {
        log.info("Intercept create todo request {}", todoRequest);
        return new ResponseEntity<>(todoService.create(todoRequest), HttpStatus.CREATED);
    }
	
	@PreAuthorize("hasAuthority('USER')")
	@PostMapping("/todos/batch")
    public ResponseEntity<Object> batchCreate(@RequestBody TodoBatch todoBatch) {
        return new ResponseEntity<>(new Object(), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TodoResponse> update(@PathVariable Long id,
                                               @RequestBody TodoRequest todoRequest) {
        log.info("Intercept update todo request {}", todoRequest);
        return new ResponseEntity<>(todoService.update(todoRequest, id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        log.info("Intercept delete todo request {}", id);
        todoService.delete(id);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TodoResponse> checkById(@PathVariable Long id) {
        log.info("Intercept check by id todo request {}", id);
        return new ResponseEntity<>(todoService.get(id), HttpStatus.OK);
    }
    
    @GetMapping()
    public ResponseEntity<Object> getAll(@RequestParam(defaultValue = "0") Integer page,
                                         @RequestParam(defaultValue = "10") Integer size) {
        log.info("Intercept get all todo request");
        return new ResponseEntity<>(todoService.getAll(page, size), HttpStatus.OK);
    }
	
	
}
