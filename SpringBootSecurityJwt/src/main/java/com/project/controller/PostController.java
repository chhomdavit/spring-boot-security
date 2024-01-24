package com.project.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.project.constant.AppConstant;
import com.project.dto.PostReponseDto;
import com.project.dto.PostDto;
import com.project.exception.EmptyFileException;
import com.project.service.PostService;

@RestController
@RequestMapping("/api/v1/auth/")
@CrossOrigin(origins = "http://localhost:1001")
public class PostController {

	private final PostService postService;
    public PostController(PostService postService) {
		super();
		this.postService = postService;
	}


	@PostMapping("/post")
    public ResponseEntity<PostDto> createPost(
            @RequestParam("file") MultipartFile file,
            @RequestParam("title") String title,
            @RequestParam("description") String description
    		) throws IOException, EmptyFileException {

    	if(file.isEmpty()) {
    		throw new EmptyFileException("File is empty! Please send another file");
    	}
    	
    	 LocalDateTime created = LocalDateTime.now(); 
    	 LocalDateTime updated = LocalDateTime.now(); 

    	PostDto postRequestDto = new PostDto(title,description,null,null,created,updated);
        return new ResponseEntity<>(postService.create(postRequestDto, file), HttpStatus.CREATED);
    }
	
	
	@PutMapping("/post/{id}")
	public ResponseEntity<PostDto> update(
	        @PathVariable Long id,
	        @RequestParam(value = "file", required = false) MultipartFile file,
	        @RequestParam("title") String title,
            @RequestParam("description") String description
	      
	) throws IOException, JsonProcessingException {
		if (file != null && file.isEmpty()) {
	        file = null;
	    }
		
		LocalDateTime created = LocalDateTime.now(); 
   	    LocalDateTime updated = LocalDateTime.now(); 
		
		PostDto postRequestDto = new PostDto(title,description,null,null,created,updated);

	    return ResponseEntity.ok(postService.update(id, postRequestDto, file));
	}
	
	
	@GetMapping("/post")
	public ResponseEntity<List<PostDto>> getAll(){
		return ResponseEntity.ok(postService.getAll());
	}
	
	
	@GetMapping("/post/{id}")
	public ResponseEntity<PostDto> getById(@PathVariable Long id){
		return ResponseEntity.ok(postService.getById(id));
	}
	
	
	@DeleteMapping("/post/{id}")
	public ResponseEntity<String> delete(@PathVariable Long id) throws IOException {
		return ResponseEntity.ok(postService.delete(id));
	}
	
	@GetMapping("/post/allPagination")
	public ResponseEntity<PostReponseDto> getAllPagination(
				@RequestParam(defaultValue = AppConstant.PAGE_NUMBER, required = false) Integer pageNumber,
				@RequestParam(defaultValue = AppConstant.PAGE_SIZE, required = false) Integer pageSize
			){
		return ResponseEntity.ok(postService.getAllWithPagination(pageNumber, pageSize));
	}
	
	
	@GetMapping("/post/allPaginationAndSort")
	public ResponseEntity<PostReponseDto> getWithPaginationAndSorting(
				@RequestParam(defaultValue = AppConstant.PAGE_NUMBER, required = false) Integer pageNumber,
				@RequestParam(defaultValue = AppConstant.PAGE_SIZE, required = false) Integer pageSize,
				@RequestParam(defaultValue = AppConstant.SORT_BY, required = false) String sortBy,
				@RequestParam(defaultValue = AppConstant.SORT_DIR, required = false) String dir
			){
		return ResponseEntity.ok(postService.getAllWithPaginationAndSorting(pageNumber, pageSize, sortBy, dir));
	}
	
	
	@GetMapping("/post/search")
    public ResponseEntity<PostReponseDto> search(
            @RequestParam String keyword,
            @RequestParam(defaultValue = AppConstant.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(defaultValue = AppConstant.PAGE_SIZE, required = false) Integer pageSize
    ) {
        return ResponseEntity.ok(postService.search(keyword, pageNumber, pageSize));
    }
	

	
	
	
}
