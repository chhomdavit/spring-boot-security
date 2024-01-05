package com.project.service.impl;


import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.project.dto.BookRequestDto;
import com.project.dto.BookResponseDto;

import com.project.model.Author;
import com.project.model.Book;
import com.project.model.Category;
import com.project.model.PageClazz;
import com.project.model.Photo;

import com.project.repository.BookRepository;
import com.project.repository.PageclazzRepository;
import com.project.service.AuthorService;
import com.project.service.BookService;
import com.project.service.CategoryService;
import com.project.service.PhotoService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@RequiredArgsConstructor
@Service
public class BookServiceImpl  implements BookService{

	private final ModelMapper modelMapper;
    private final AuthorService authorService;
    private final BookRepository bookRepository;
    private final PhotoService photoService;
    private final CategoryService categoryService;
    private final PageclazzRepository pageclazzRepository;
	
	@Override
	public void createNewBook(BookRequestDto bookRequestDto) throws Exception {

		Author author = authorService.findById(bookRequestDto.getAuthorId());
        if(Objects.isNull(author)) {
            log.error("Author {} not found.", bookRequestDto.getPhotoId());
            throw new Exception();
        }
        
        Photo photo = photoService.checkById(bookRequestDto.getPhotoId());
        if(Objects.isNull(photo)) {
            log.error("PHOTO-ID {} not found.", bookRequestDto.getPhotoId());
        }

        Category category = categoryService.checkById(bookRequestDto.getCategoryId());
        if(Objects.isNull(category)) {
            log.error("CATEGORY-ID {} not found.", bookRequestDto.getCategoryId());
            throw new Exception();
        }
        
        PageClazz pageClazz = modelMapper.map(bookRequestDto.getPageDto(), PageClazz.class);
        Book book = new Book();
        book.setTitle(bookRequestDto.getTitle());
        book.setIsbn(bookRequestDto.getIsbn());
        book.setPublishedDate(bookRequestDto.getPublishedDate());
        book.setRating(bookRequestDto.getRating());
        book.setAuthors(Collections.singletonList(author));
        book.setCategories(Collections.singletonList(category));
        book.setPhoto(photo);
        bookRepository.save(book);
        pageClazz.setBook(book);
        pageclazzRepository.save(pageClazz);
		
	}

	@Override
	public List<BookResponseDto> listBooks() throws Exception {
		 return bookRepository.findAll().stream()
	                .map(book -> modelMapper.map(book, BookResponseDto.class))
	                .collect(Collectors.toList());
	    }
	

}
