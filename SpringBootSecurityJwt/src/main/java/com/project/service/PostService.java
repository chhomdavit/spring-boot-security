package com.project.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.project.dto.PostReponseDto;
import com.project.dto.PostDto;


public interface PostService {

    PostDto create(PostDto postRequestDto, MultipartFile file) throws IOException;
	
	PostDto getById(Long id);
	
	List<PostDto> getAll();
	
	PostDto update(Long id, PostDto postRequestDto, MultipartFile file) throws IOException;
	
	String delete(Long id) throws IOException;
	
	PostReponseDto getAllWithPagination(Integer pageNumber, Integer pageSize);
	
	PostReponseDto getAllWithPaginationAndSorting( Integer pageNumber, Integer pageSize,
	   String sortBy, String dir);
	
	PostReponseDto search(String keyword, Integer pageNumber, Integer pageSize);
	
}
