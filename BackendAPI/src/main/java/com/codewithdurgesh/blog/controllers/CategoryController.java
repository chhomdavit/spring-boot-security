package com.codewithdurgesh.blog.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.RequestScope;

import com.codewithdurgesh.blog.payloads.ApiResponse;
import com.codewithdurgesh.blog.payloads.CategoryDto;
import com.codewithdurgesh.blog.services.CategoryService;


@RestController
@RequestMapping("/api/categories")
public class CategoryController {
	@Autowired
	CategoryService catService;
	//create
	@PostMapping("/")
	public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto catDto) {
		CategoryDto cretedCategory=catService.createCategory(catDto);
		return new ResponseEntity<CategoryDto>(cretedCategory, HttpStatus.CREATED);
	}
	//update
		@PutMapping("/{id}")
		public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto catDto,@ PathVariable Integer id) {
			CategoryDto cretedCategory=catService.updateCategory(catDto,id);
			return new ResponseEntity<CategoryDto>(cretedCategory, HttpStatus.OK);
		}
		//delete
				@DeleteMapping("/{id}")
				public ResponseEntity<ApiResponse> deleteCategory(@ PathVariable Integer id) {
					catService.deleteCategory(id);
					return new ResponseEntity<ApiResponse>(new ApiResponse("Category deleted successfully", true),
							HttpStatus.OK);
				}
		//getCategory
		@GetMapping("/{id}")
		public ResponseEntity<CategoryDto> getCategory(@ PathVariable Integer id){
			CategoryDto catDto=catService.getCategory(id);
			return new ResponseEntity<CategoryDto>(catDto, HttpStatus.OK);
		}
		//getCategories
				@GetMapping("/")
				public ResponseEntity<List<CategoryDto>> getCategories(){
					List<CategoryDto> catDto=catService.getCategories();
					return  ResponseEntity.ok(catDto);
				}
}
