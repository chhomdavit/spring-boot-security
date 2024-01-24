package com.project.service.Impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.project.dto.PostReponseDto;
import com.project.dto.PostDto;
import com.project.exception.FileExistsException;
import com.project.exception.PostNotFoundException;
import com.project.model.Post;
import com.project.repository.PostRepository;
import com.project.service.FileService;
import com.project.service.PostService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService{

	private final PostRepository postRepository;
	private final FileService fileService;
	private final ModelMapper modelMapper;
	
	@Value("${project.poster}")
	private String path;
	
	@Value("${base.url}")
	private String baseUrl;
	
	@Override
	public PostDto create(PostDto postRequestDto, MultipartFile file) throws IOException {
		// TODO Auto-generated method stub
		
		if (Files.exists(Paths.get(path + File.separator + file.getOriginalFilename()))) {
	        throw new FileExistsException("File already exists! Please enter another file name");
	    }
		
		String uploadFileName =  fileService.uploadFile(path, file);
		
		postRequestDto.setImg_post(uploadFileName);
		
		Post post = modelMapper.map(postRequestDto, Post.class);
		
		post.setCreated(LocalDateTime.now());
		
		Post savedPost = postRepository.save(post);
		
		savedPost.setUpdated(LocalDateTime.now());
		
		String img_postUrl = baseUrl + "/file/" + uploadFileName;
		
		PostDto respose = modelMapper.map(savedPost, PostDto.class);
		respose.setImg_postUrl(img_postUrl);
		
		return respose;
	}

	@Override
	public PostDto getById(Long id) {
		// TODO Auto-generated method stub
		
		Post post = postRepository.findById(id)
			.orElseThrow(()-> new PostNotFoundException("Movie not found with id" + id));
				
		PostDto response = modelMapper.map(post, PostDto.class);

		response.setImg_postUrl(baseUrl + "/file/" + post.getImg_post());

		return response;
	}
	

	@Override
	public List<PostDto> getAll() {
		// TODO Auto-generated method stub
		
        List<Post> posts = postRepository.findAll();

        List<PostDto> postRequestDtos = posts.stream()
                .map(post -> {
                	PostDto postRequestDto = modelMapper.map(post, PostDto.class);
                	postRequestDto.setImg_postUrl(baseUrl + "/file/" + post.getImg_post());
                    return postRequestDto;
                })
                .collect(Collectors.toList());

        return postRequestDtos;
	}

	@Override
	public PostDto update(Long id, PostDto postRequestDto, MultipartFile file) throws IOException {
		// TODO Auto-generated method stub
		
	    Post CheckPostId = postRepository.findById(id)
	            .orElseThrow(() -> new PostNotFoundException("Movie not found with id" + id));

	    
	    String fileName = CheckPostId.getImg_post();
	    if (file != null) {
	        // Delete the old file only if the new file is not null
	        try {
	            Files.deleteIfExists(Paths.get(path + File.separator + fileName));
	        } catch (IOException e) {
	            // Handle or log the exception as needed
	            e.printStackTrace();
	        }
	        fileName = fileService.uploadFile(path, file);
	    }

	    
	    postRequestDto.setImg_post(fileName);

	    Post updated = modelMapper.map(postRequestDto, Post.class);
	    updated.setId(CheckPostId.getId()); 

	    Post saved = postRepository.save(updated);
	    
	    String img_postUrl = baseUrl + "/file/" + fileName;
	    
	    PostDto response = modelMapper.map(saved, PostDto.class);
	    response.setImg_postUrl(img_postUrl);

	    return response;
	}

	@Override
	public String delete(Long id) throws IOException {
		// TODO Auto-generated method stub
		
		Post CheckPostId = postRepository.findById(id)
		         .orElseThrow(() -> new PostNotFoundException("Movie not found with id" + id));

	    Long postID = CheckPostId.getId();


		    Path filePath = Paths.get(path, CheckPostId.getImg_post());
		    if (Files.exists(filePath)) {
		        try {
		            Files.delete(filePath);
		        } catch (IOException e) {
		            System.err.println("Error deleting file: " + e.getMessage());
		            // Optional: Add retry logic here
		        }
		    }

		postRepository.delete(CheckPostId);

	    return "post deleted with id = " + postID;
	}
	

	@Override
	public PostReponseDto getAllWithPagination(Integer pageNumber, Integer pageSize) {
		// TODO Auto-generated method stub
		Pageable pageable = PageRequest.of(pageNumber, pageSize);
		
		Page<Post> PostPages = postRepository.findAll(pageable);
		List<Post> posts = PostPages.getContent();
		List<PostDto> postRequestDtos = new ArrayList<>();

		for(Post post: posts) {
			String img_postUrl = baseUrl + "/file/" + post.getImg_post();
			PostDto postRequestDto = modelMapper.map(post, PostDto.class);
			postRequestDto.setImg_postUrl(img_postUrl);
			postRequestDtos.add(postRequestDto);
		}
		return new PostReponseDto( 
				postRequestDtos, pageNumber, pageSize, 
				PostPages.getTotalElements(),
				PostPages.getTotalPages(),
				PostPages.isLast()); 
	}

	@Override
	public PostReponseDto getAllWithPaginationAndSorting(Integer pageNumber, Integer pageSize, String sortBy, String dir) {
	    
		Sort sort = dir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
	    Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);  
	    Page<Post> postPages = postRepository.findAll(pageable);

	    List<PostDto> postRequestDtos = postPages.getContent().stream().map(post -> {
	        PostDto postRequestDto = modelMapper.map(post, PostDto.class);
	        postRequestDto.setImg_postUrl(baseUrl + "/file/" + post.getImg_post());
	        return postRequestDto;
	    }).collect(Collectors.toList());
	    
	    return new PostReponseDto(
	            postRequestDtos, pageNumber, pageSize, 
	            postPages.getTotalElements(), 
	            postPages.getTotalPages(),
	            postPages.isLast());
	}

	@Override
	public PostReponseDto search(String keyword, Integer pageNumber, Integer pageSize) {
		// TODO Auto-generated method stub
		
		Pageable pageable = PageRequest.of(pageNumber, pageSize);
	    Page<Post> postPages = postRepository.findByTitleContainingIgnoreCase(keyword, pageable);
	    List<PostDto> postRequestDtos = postPages.getContent().stream().map(post -> {
	        PostDto postRequestDto = modelMapper.map(post, PostDto.class);
	        postRequestDto.setImg_postUrl(baseUrl + "/file/" + post.getImg_post());
	        return postRequestDto;
	    }).collect(Collectors.toList());
	    
	    return new PostReponseDto(
	            postRequestDtos, pageNumber, pageSize, 
	            postPages.getTotalElements(),
	            postPages.getTotalPages(), 
	            postPages.isLast());
	}

}
