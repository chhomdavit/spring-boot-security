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

import com.project.dto.ProductDTO;
import com.project.exception.ResourceNotFoundException;
import com.project.model.Product;
import com.project.repository.ProductRepository;
import com.project.service.ProductService;
import com.project.service.StorageService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{
	
    private final ProductRepository productRepository;
	private final StorageService storageService;
	private final ModelMapper modelMapper;
	
	@Value("${project.upload}")
	private String path;
	
	@Value("${base.url}")
	private String baseUrl;

	@Override
	public ProductDTO createProduct(ProductDTO productDTO, MultipartFile file) throws IOException {
		
		String uploadFileName = storageService.uploadFile(path ,file);

		productDTO.setProductImage(uploadFileName);

	    Product product = modelMapper.map(productDTO, Product.class);

	    Product saved = productRepository.save(product);

	    String imagerUrl = baseUrl + "/api/v1/auth/file/" + uploadFileName;

	    ProductDTO response = modelMapper.map(saved, ProductDTO.class);
	    
	    response.setProductImageUrl(imagerUrl);

	    return response;
	}

	@Override
	public List<ProductDTO> getAllProduct() {
		List<Product> products = productRepository.findAll();
        List<ProductDTO> productDTOs = products.stream()
        		
                .map(product -> {
                	ProductDTO productDTO = modelMapper.map(product, ProductDTO.class);
                	
                	productDTO.setProductImageUrl(baseUrl + "/api/v1/auth/file/" + product.getProductImage());
                    
                    return productDTO;
                })
                .collect(Collectors.toList());

        return productDTOs;
	}

	
	@Override
	public ProductDTO updateProduct(Integer productId, ProductDTO productDTO, MultipartFile file) throws IOException {
		
		Product existingProduct = this.productRepository.findById(productId)
	            .orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));
	    
	    String uploadFileName = existingProduct.getProductImage();
	    if (file != null && !file.isEmpty()) {
	        try (InputStream in = file.getInputStream();
	             OutputStream out = Files.newOutputStream(Paths.get(path + File.separator + uploadFileName))) {
	            byte[] buffer = new byte[1024];
	            int len;
	            while ((len = in.read(buffer)) > 0) {
	                out.write(buffer, 0, len);
	            }
	        }
	    }
	    
	    productDTO.setProductImage(uploadFileName);

	    Product updated = modelMapper.map(productDTO, Product.class);
	    
	    updated.setProductId(existingProduct.getProductId()); 

	    Product saved = productRepository.save(updated);

	    String imagerUrl = baseUrl + "/api/v1/auth/file/" + uploadFileName;

	    ProductDTO response = modelMapper.map(saved, ProductDTO.class);
	    
	    response.setProductImageUrl(imagerUrl);;

	    return response;
	}

	@Override
	public String deleteProduct(Integer productId) throws IOException {
		
		Product existingProduct = this.productRepository.findById(productId)
	            .orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));
	    
	    File file = new File(path + File.separator + existingProduct.getProductImage());
	    FileUtils.forceDelete(FileUtils.getFile(file));
	    
	    productRepository.delete(existingProduct);
	    return "Post deleted with id = " + existingProduct;
	}
	
	
	

//	@Override
//	public String deleteProduct(Integer productId) throws IOException {
//		
//		Product existingProduct = this.productRepository.findById(productId)
//	            .orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));
//	    
//	    String filePath = path + File.separator + existingProduct.getProductImage();
//	    storageService.deleteFile(filePath);
//	    
//	    productRepository.delete(existingProduct);
//	    return "Post deleted with id = " + existingProduct;
//	}
	
	


	
	
}
