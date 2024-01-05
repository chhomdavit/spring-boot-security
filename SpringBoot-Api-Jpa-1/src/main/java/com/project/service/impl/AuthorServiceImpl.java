package com.project.service.impl;

import java.util.Objects;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.project.dto.AuthorRequestDto;
import com.project.model.Author;
import com.project.repository.AuthorRepository;
import com.project.service.AuthorService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuthorServiceImpl implements AuthorService {

    private final ModelMapper modelMapper;
    private final AuthorRepository authorRepository;
    
    
	@Override
	public void add(AuthorRequestDto authorRequestDto) throws Exception {
		Author author = authorRepository.save(modelMapper.map(authorRequestDto, Author.class));
        if (Objects.isNull(author.getId())) {
            log.error("Saving new author was failed!");
            throw new Exception();
        }
		
	}
	@Override
	public Author findById(Long id) throws Exception {
		 Optional<Author> author = authorRepository.findFirstById(id);
	     return author.orElse(null);
	}



}
