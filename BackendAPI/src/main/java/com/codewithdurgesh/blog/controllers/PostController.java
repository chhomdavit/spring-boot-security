package com.codewithdurgesh.blog.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.codewithdurgesh.blog.config.AppConstants;
import com.codewithdurgesh.blog.entities.Post;
import com.codewithdurgesh.blog.payloads.ApiResponse;
import com.codewithdurgesh.blog.payloads.PostDto;
import com.codewithdurgesh.blog.payloads.PostResponse;
import com.codewithdurgesh.blog.services.FileService;
import com.codewithdurgesh.blog.services.PostService;

@RestController
@RequestMapping("/api")
public class PostController {
	@Autowired
	PostService postservice;
	@Autowired
	FileService fileService;

	@Value("${project.image}")
	String path;
	private PostDto post;
	//create
	@PostMapping("/user/{userId}/category/{categoryId}/posts")
	ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto, @PathVariable Integer userId,
			@PathVariable Integer categoryId){
		PostDto cretePost=postservice.createPost(postDto, userId, categoryId);
		
		return new ResponseEntity<PostDto>(cretePost,HttpStatus.CREATED);
		
	}
	//get post by user
	@GetMapping("/user/{userId}/posts")
	public ResponseEntity<List<PostDto>> getPostsByUser(@PathVariable Integer userId){
		List<PostDto> posts=postservice.getPostsByUser(userId);
		return new ResponseEntity<List<PostDto>>(posts,HttpStatus.OK);
		
	}
	//get post by category
		@GetMapping("/category/{categoryId}/posts")
		public ResponseEntity<List<PostDto>> getPostsByCategory(@PathVariable Integer categoryId){
			List<PostDto> posts=postservice.getPostsByCategory(categoryId);
			return new ResponseEntity<List<PostDto>>(posts,HttpStatus.OK);
			
		}
	//get all posts
	@GetMapping("/posts")
	public ResponseEntity<PostResponse> getAllPost(
			@RequestParam(value="pageNumber",defaultValue=AppConstants.PAGE_NUMBER,required=false) Integer pageNumber,
			@RequestParam(value="pageSize",defaultValue=AppConstants.PAGE_SIZE,required=false) Integer pageSize,
			@RequestParam(value="sortBy",defaultValue=AppConstants.SORT_BY,required=false) String sortBy,
			@RequestParam(value="sortDir",defaultValue=AppConstants.SORT_DIR,required=false) String sortDir
			){
		
		return new ResponseEntity<PostResponse>(this.postservice.getAllPost(pageNumber, pageSize,sortBy,sortDir), HttpStatus.OK);
		
	}
	//get post by id
	@GetMapping("/posts/{postId}")
     public ResponseEntity<PostDto> getPostById(@PathVariable Integer postId){
		
		return new ResponseEntity<PostDto>(this.postservice.getPost(postId), HttpStatus.OK);
     }
	//delete
	@DeleteMapping("/posts/{postId}")
	public ApiResponse deletePost(@PathVariable Integer postId) {
		this.postservice.deletePost(postId);
		return new ApiResponse("post deleted", true);
		
	}
	//PUt Mapping
		@PutMapping("/posts/{postId}")
		public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto, @PathVariable Integer postId) {
			PostDto updatedPost=this.postservice.updatePost(postDto, postId);
			return new ResponseEntity<PostDto>(updatedPost, HttpStatus.OK);
			
		}
	//search
		@GetMapping("/posts/search/{keywords}")
		public ResponseEntity<List<PostDto>> searchPostByTitle(@PathVariable("keywords") String keywords){
			List<PostDto> result = this.postservice.searchPost(keywords);
			return new ResponseEntity<List<PostDto>>(result,HttpStatus.OK);	
		}
	//Post Image Upload
		@PostMapping("/post/image/upload/{postId}")
		public ResponseEntity<PostDto> uploadPostImage (@RequestParam("image") MultipartFile image,
				@PathVariable Integer postId) throws IOException{
			PostDto postDto = this.postservice.getPost(postId);
			String fileName = this.fileService.uploadFile (path, image);
			 
			 postDto.setImageName(fileName);
			 PostDto updatePost = this.postservice.updatePost(postDto, postId);
			 return new ResponseEntity<PostDto>(updatePost, HttpStatus.OK);
		}
		@GetMapping(value="/post/image/{imageName}",produces=MediaType.IMAGE_JPEG_VALUE)
		public void downloadImage(
				@PathVariable("imageNmae") String imageName,
				HttpServletResponse response
				) throws IOException {
			InputStream resource=this.fileService.getResource(path, imageName);
			response.setContentType(MediaType.IMAGE_JPEG_VALUE);
			StreamUtils.copy(resource, response.getOutputStream());
		}
		   
		
}
