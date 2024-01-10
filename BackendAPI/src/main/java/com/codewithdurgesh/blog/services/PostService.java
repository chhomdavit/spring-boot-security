package com.codewithdurgesh.blog.services;

import java.util.List;
import com.codewithdurgesh.blog.payloads.PostDto;
import com.codewithdurgesh.blog.payloads.PostResponse;

public interface PostService {
	//create
	PostDto createPost(PostDto postDto,Integer userId, Integer categoryId);
	
	//updatePost
	PostDto updatePost(PostDto postDto, Integer postId);
	
	//delete
	void deletePost(Integer postId);
	
	//list of post
	PostResponse getAllPost(Integer pageNumber, Integer pageSize,String sortBy,String sortDir);
	//get single post
	PostDto getPost(Integer postId);
	//list of posts by category
	List<PostDto> getPostsByCategory(Integer categoryId);
	//list of posts by User
	List<PostDto> getPostsByUser(Integer userId);
	//searchPost
	List<PostDto> searchPost(String keyword);
	
}
