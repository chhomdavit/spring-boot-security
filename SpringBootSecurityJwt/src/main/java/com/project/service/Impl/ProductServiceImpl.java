package com.project.service.Impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.project.dto.ProductDto;
import com.project.dto.ProductReponseDto;
import com.project.exception.FileExistsException;
import com.project.model.Product;
import com.project.repository.ProductRepository;
import com.project.service.FileService;
import com.project.service.ProductService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{
	
	private final ProductRepository productRepository;
	private final FileService fileService;
	private final ModelMapper modelMapper;
	
	@Value("${project.poster}")
	private String path;
	
	@Value("${base.url}")
	private String baseUrl;


	@Override
	public ProductDto create(ProductDto productDto, MultipartFile file) throws IOException {
		// TODO Auto-generated method stub
		
		if (Files.exists(Paths.get(path + File.separator + file.getOriginalFilename()))) {
	        throw new FileExistsException("File already exists! Please enter another file name");
	    }
		String uploadFileName =  fileService.uploadFile(path, file);
		
		productDto.setImg_product(uploadFileName);
		
		Product product = modelMapper.map(productDto, Product.class);
		
		Product savedProduct = productRepository.save(product);
		
		String img_productUrl = baseUrl + "/file/" + uploadFileName;
		
		ProductDto respose = modelMapper.map(savedProduct, ProductDto.class);
		respose.setImg_productUrl(img_productUrl);
		
		return respose;
	}
	
	@Override
	public ProductDto getById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public List<ProductDto> getAll() {
		// TODO Auto-generated method stub
        List<Product> products = productRepository.findAll();

        List<ProductDto> productDtos = products.stream()
                .map(product -> {
                	ProductDto productDto = modelMapper.map(product, ProductDto.class);
                	productDto.setImg_productUrl(baseUrl + "/file/" + product.getImg_product());
                    return productDto;
                })
                .collect(Collectors.toList());

        return productDtos;
	}
	
	@Override
	public ProductDto update(Long id, ProductDto productDto, MultipartFile file) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public String delete(Long id) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public ProductReponseDto getAllWithPagination(Integer pageNumber, Integer pageSize) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public ProductReponseDto getAllWithPaginationAndSorting(Integer pageNumber, Integer pageSize, String sortBy,
			String dir) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public ProductReponseDto search(String keyword, Integer pageNumber, Integer pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

	
	
	
	
	
	
//	@Override
//	public ProductDto create(ProductDto productDto) throws IOException {
//		// TODO Auto-generated method stub
//	
//	    Product product = modelMapper.map(productDto, Product.class);
//	    Product saved = productRepository.save(product);
//
//	    ProductDto response = modelMapper.map(saved, ProductDto.class);
//	    return response;
//	}
}
