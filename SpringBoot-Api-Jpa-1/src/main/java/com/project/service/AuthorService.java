package com.project.service;

import com.project.dto.AuthorRequestDto;
import com.project.model.Author;

public interface AuthorService {

	void add(AuthorRequestDto authorRequestDto) throws Exception;

    Author findById(Long id) throws Exception;
}
