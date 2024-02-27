package com.project.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.project.dto.PostDTO;
import com.project.model.User;
import com.project.service.PostService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth/posts")
public class PostController {

	private final PostService postService;
	
	@PreAuthorize("hasAuthority('USER')")
    @PostMapping("/create")
    public ResponseEntity<PostDTO> createPost(
            @RequestParam("file") MultipartFile file,
            @RequestParam String title,
            @RequestParam String content,
            Authentication authentication
    ) {
        User user = (User) authentication.getPrincipal();

        PostDTO postDTO = new PostDTO(null, title, content, null, null, LocalDateTime.now(), LocalDateTime.now(), null);
        try {
            PostDTO createdPost = postService.createPost(postDTO, user.getUserId(), file);
            return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
        } catch (IOException e) {
            // Handle IOException
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
	
	
	@PreAuthorize("hasAuthority('USER')")
	@PutMapping("/update/{postId}")
	public PostDTO updatePost(
	        @PathVariable Integer postId,
	        @RequestParam String title,
	        @RequestParam String content,
	        @RequestParam(required = false, name = "file") MultipartFile file,
	        Authentication authentication
	        
			) throws IOException {
		
		User user = (User) authentication.getPrincipal();
		PostDTO postDTO = new PostDTO(null, title, content, null, null, LocalDateTime.now(), LocalDateTime.now(), null);
	    return postService.updatePost( postId,postDTO, user.getUserId(),file);
	}
	
	
	@PreAuthorize("hasAuthority('USER')")
    @DeleteMapping("/delete/{postId}")
    public ResponseEntity<String> deletePost(
            @PathVariable Integer postId,
            Authentication authentication
    ) throws IOException {
        User user = (User) authentication.getPrincipal();
        
        String deletePost = postService.deletePost(postId, user.getUserId());
        return ResponseEntity.ok(deletePost);
    }
	
	
	
	
	@GetMapping("")
    public List<PostDTO> getAllProducts() {
        return postService.getAllPost();
    }
	
	
}
