package com.project.service.Impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.project.dto.PostDTO;
import com.project.exception.ResourceNotFoundException;
import com.project.model.Post;
import com.project.model.User;
import com.project.repository.PostRepository;
import com.project.repository.UserRepository;
import com.project.service.PostService;
import com.project.service.StorageService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService{
	
	private final PostRepository postRepository;
	private final UserRepository userRepository;
	private final StorageService storageService;
	private final ModelMapper modelMapper;
	
	@Value("${project.upload}")
	private String path;
	
	@Value("${base.url}")
	private String baseUrl;
	
	
	@Override
	public PostDTO createPost(PostDTO postDTO, Integer userId, MultipartFile file) throws IOException {
		
		User user=this.userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "userID", userId));
	
		String uploadFileName =  storageService.uploadFile(path, file);;
		postDTO.setPostImage(uploadFileName);

	    Post post = modelMapper.map(postDTO, Post.class);
	    post.setUser(user);
	    
	    Post savedPost = postRepository.save(post);
	    String imagerUrl = baseUrl + "/api/v1/auth/file/" + uploadFileName;

	    PostDTO response = modelMapper.map(savedPost, PostDTO.class);
	    response.setPostImgUrl(imagerUrl);
	    return response;
	
	}

	
	@Override
	public PostDTO updatePost(Integer postId, PostDTO postDTO, Integer userId, MultipartFile file) throws IOException {
		
		User user = this.userRepository.findById(userId)
	            .orElseThrow(() -> new ResourceNotFoundException("User", "userID", userId));
		
		Post existingPost = this.postRepository.findById(postId)
	            .orElseThrow(() -> new ResourceNotFoundException("Post", "postId", postId));
		
		String fileName = existingPost.getPostImage();
	    if (file != null && !file.isEmpty()) {
	        try (InputStream in = file.getInputStream();
	             OutputStream out = Files.newOutputStream(Paths.get(path + File.separator + fileName))) {
	            byte[] buffer = new byte[1024];
	            int len;
	            while ((len = in.read(buffer)) > 0) {out.write(buffer, 0, len);}
	        }
	    }
	    postDTO.setPostImage(fileName);
		
	    if (!existingPost.getUser().equals(user)) {
	        throw new ResourceNotFoundException("Post", "postId", postId);
	    }
		
        Post post = modelMapper.map(postDTO, Post.class);
        post.setUser(user);
        post.setPostId(existingPost.getPostId());

        Post saved = postRepository.save(post);

	    String imagerUrl = baseUrl + "/api/v1/auth/file/" + fileName;

	    PostDTO response = modelMapper.map(saved, PostDTO.class);
	        
	    response.setPostImgUrl(imagerUrl);

	    return response;
	}
	
	
	@Override
	public String deletePost(Integer postId, Integer userId) throws IOException {
		
		Post existingPost = this.postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "postId", postId));

        if (!existingPost.getUser().getUserId().equals(userId)) {
            throw new ResourceNotFoundException("Post", "postId", postId);
        }       
 	   
	    File file = new File(path + File.separator + existingPost.getPostImage());

	    FileUtils.forceDelete(FileUtils.getFile(file));

        postRepository.delete(existingPost);
        
        return "Product with ID " + postId + " has been successfully deleted.";
	}

	
	@Override
	public List<PostDTO> getAllPost() {
		List<Post> posts = postRepository.findAll();
        List<PostDTO> postDTOs = posts.stream()
        		
                .map(post -> {
                	PostDTO postDTO = modelMapper.map(post, PostDTO.class);
                	
                	postDTO.setPostImgUrl(baseUrl + "/api/v1/auth/file/" + post.getPostImage());
                    
                    return postDTO;
                })
                .collect(Collectors.toList());

        return postDTOs;
	}

}
