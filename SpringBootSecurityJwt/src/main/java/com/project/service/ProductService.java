package com.project.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.project.dto.ProductDto;
import com.project.dto.ProductReponseDto;



public interface ProductService {

	ProductDto create(ProductDto productDto, MultipartFile file) throws IOException;
	
	ProductDto getById(Long id);
	
	List<ProductDto> getAll();
	
	ProductDto update(Long id, ProductDto productDto, MultipartFile file) throws IOException;
	
	String delete(Long id) throws IOException;
	
	ProductReponseDto getAllWithPagination(Integer pageNumber, Integer pageSize);
	
	ProductReponseDto getAllWithPaginationAndSorting( Integer pageNumber, Integer pageSize,
														   String sortBy, String dir);

	ProductReponseDto search(String keyword, Integer pageNumber, Integer pageSize);

}
