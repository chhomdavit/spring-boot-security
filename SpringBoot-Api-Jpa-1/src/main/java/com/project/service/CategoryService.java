package com.project.service;

import com.project.dto.CategoryRequestDto;
import com.project.model.Category;

public interface CategoryService {

    void createNewCategory(CategoryRequestDto categoryRequestDto) throws Exception;

    Category checkById(Long id) throws Exception;
}
