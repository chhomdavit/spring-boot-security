package com.project.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.project.dto.ProductDto;
import com.project.exception.EmptyFileException;
import com.project.service.ProductService;



@RestController
@RequestMapping("/api/v1/auth/")
@CrossOrigin(origins = "http://localhost:1001")
public class ProductController {
  
	private final ProductService productService;
	public ProductController(ProductService productService) {
		super();
		this.productService = productService;
	}


	@PreAuthorize("hasAuthority('USER')")
    @PostMapping("/product")
    public ResponseEntity<ProductDto> createProduct(
            @RequestParam("file") MultipartFile file,
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("status") String status
    		) throws IOException, EmptyFileException {

    	if(file.isEmpty()) {
    		throw new EmptyFileException("File is empty! Please send another file");
    	}
    	ProductDto productDto = new ProductDto(title,description,null,null,status);
     return new ResponseEntity<>(productService.create(productDto, file), HttpStatus.CREATED);
    }


	@GetMapping("/product")
	public ResponseEntity<List<ProductDto>> getAll(){
		return ResponseEntity.ok(productService.getAll());
	}

	
	
	
	
	
	
	
	
	
	
//    @PreAuthorize("hasAuthority('USER')")
//    @PostMapping("/product")
//    public ResponseEntity<ProductDto> create(
//            @RequestBody ProductDto productDto) throws IOException {
//
//     return new ResponseEntity<>(productService.create(productDto), HttpStatus.CREATED);
//    }
}
