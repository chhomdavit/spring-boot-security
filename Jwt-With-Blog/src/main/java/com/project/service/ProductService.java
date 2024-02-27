package com.project.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.project.dto.ProductDTO;

public interface ProductService {

    ProductDTO createProduct(ProductDTO productDTO ,MultipartFile file) throws IOException;
	
	List<ProductDTO> getAllProduct();
	
	ProductDTO updateProduct(Integer productId, ProductDTO productDTO, MultipartFile file) throws IOException;
	
	String deleteProduct(Integer productId) throws IOException;
}
