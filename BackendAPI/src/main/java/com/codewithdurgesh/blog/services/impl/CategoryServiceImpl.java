package com.codewithdurgesh.blog.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codewithdurgesh.blog.entities.Category;
import com.codewithdurgesh.blog.exceptions.ResourceNotFoundException;
import com.codewithdurgesh.blog.payloads.CategoryDto;
import com.codewithdurgesh.blog.payloads.UserDto;
import com.codewithdurgesh.blog.repositories.CategoryRepo;
import com.codewithdurgesh.blog.services.CategoryService;
@Service
public class CategoryServiceImpl implements CategoryService {
	@Autowired
	CategoryRepo categoryRepo;
	@Autowired
	ModelMapper modelMapper;
	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
		Category category=modelMapper.map(categoryDto,Category.class);
		Category addedCat=categoryRepo.save(category);
		return modelMapper.map(addedCat, CategoryDto.class);
	}

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, Integer id) {
		Category category=this.categoryRepo.findById(id)
				.orElseThrow(()-> new ResourceNotFoundException("Category", "Category Id", id));
		category.setCategoryTitle(categoryDto.getCategoryTitle());
		category.setCategoryDescription(categoryDto.getCategoryDescription());
		Category updatedCat=categoryRepo.save(category);
		CategoryDto catDto=modelMapper.map(updatedCat,CategoryDto.class); 
		return catDto;
	}

	@Override
	public void deleteCategory(Integer id) {
		Category category=this.categoryRepo.findById(id)
				.orElseThrow(()-> new ResourceNotFoundException("Category", "Category Id", id));
		this.categoryRepo.delete(category);

	}

	@Override
	public CategoryDto getCategory(Integer id) {
		Category category=this.categoryRepo.findById(id)
				.orElseThrow(()-> new ResourceNotFoundException("Category", "Category Id", id));
		return modelMapper.map(category,CategoryDto.class); 
	}

	@Override
	public List<CategoryDto> getCategories() {
		List<Category> catList=this.categoryRepo.findAll();
		/*List<CategoryDto> updatedDto = null;
		for (Category category : catList) {
			 CategoryDto cDo=modelMapper.map(category,CategoryDto.class);
			 updatedDto.add(cDo);
		}*/
		List<CategoryDto> updatedDto = catList.stream().map(cat->this.modelMapper.map(cat, CategoryDto.class)).collect(Collectors.toList());
		return  updatedDto;
	}

}
