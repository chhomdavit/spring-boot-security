package com.codewithdurgesh.blog.services;

import java.util.List;

import com.codewithdurgesh.blog.payloads.CategoryDto;

public interface CategoryService {
	//create
	public CategoryDto createCategory(CategoryDto categoryDto);
	//Update
	public CategoryDto updateCategory(CategoryDto categoryDto,Integer id);
	//delete
	void deleteCategory(Integer id);
	//get
	CategoryDto getCategory(Integer id);
	//get All
	List<CategoryDto> getCategories();
}
