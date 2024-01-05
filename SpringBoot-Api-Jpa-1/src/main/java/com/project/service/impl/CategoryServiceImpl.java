package com.project.service.impl;

import java.util.Objects;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.project.dto.CategoryRequestDto;
import com.project.model.Category;
import com.project.repository.CategoryRepository;
import com.project.service.CategoryService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService{

	private final ModelMapper modelMapper;
    private final CategoryRepository categoryRepository;
    
	@Override
	public void createNewCategory(CategoryRequestDto categoryRequestDto) throws Exception {
		Category category = categoryRepository.save(modelMapper.map(categoryRequestDto, Category.class));
        if (Objects.isNull(category.getId())) {
            log.error("Saving new category was failed!");
            throw new Exception();
        }
		
	}

	@Override
	public Category checkById(Long id) throws Exception {
		 Optional<Category> category = categoryRepository.findFirstById(id);
	     return category.orElse(null);
	}

}
