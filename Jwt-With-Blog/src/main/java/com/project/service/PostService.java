package com.project.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.project.dto.PostDTO;

public interface PostService {

    PostDTO createPost(PostDTO postDTO, Integer userId, MultipartFile file) throws IOException;
	
	PostDTO updatePost(Integer postId, PostDTO postDTO, Integer userId, MultipartFile file) throws IOException;
	
	String deletePost(Integer postId, Integer userId) throws IOException;
	
	List<PostDTO> getAllPost();
}
