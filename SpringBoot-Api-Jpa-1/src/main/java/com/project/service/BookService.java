package com.project.service;

import java.util.List;

import com.project.dto.BookRequestDto;
import com.project.dto.BookResponseDto;

public interface BookService {

	void createNewBook(BookRequestDto bookRequestDto) throws Exception;

    List<BookResponseDto> listBooks() throws Exception;
}
