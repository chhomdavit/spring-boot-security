package com.project.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.project.dto.ProductDTO;
import com.project.service.ProductService;

import lombok.RequiredArgsConstructor;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth/products")
public class ProductController {

	private final ProductService productService;
	
	@PostMapping("/create")
    public ProductDTO createProduct(
            @RequestParam String title,
            @RequestParam String  description,
            @RequestParam("file") MultipartFile file) throws IOException {
        return productService.createProduct(new ProductDTO (null, title, description, null, null), file);
    }
	
	
	@GetMapping("")
    public List<ProductDTO> getAllProducts() {
        return productService.getAllProduct();
    }
	
	
	@PutMapping("/update/{productId}")
	public ProductDTO updateProduct(
	        @PathVariable Integer productId,
	        @RequestParam String title,
	        @RequestParam String  description,
	        @RequestParam(required = false, name = "file") MultipartFile file) throws IOException {
	    ProductDTO productDTO = new ProductDTO(null, title, description, null, null);
	    return productService.updateProduct(productId, productDTO, file);
	}
	
	
	@DeleteMapping("/delete/{productId}")
	public ResponseEntity<String> deleteProduct(@PathVariable Integer productId) throws IOException {
		return ResponseEntity.ok(productService.deleteProduct(productId));
	}
	
	
}
