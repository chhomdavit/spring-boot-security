package com.project.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.project.dto.MovieDto;
import com.project.dto.MoviePageResponse;
import com.project.exception.EmptyFileException;
import com.project.service.MovieService;
import com.project.util.AppConstants;

@RestController
@RequestMapping("/api/v1/movie")
public class MovieController {

	private final MovieService movieService;

	public MovieController(MovieService movieService) {
		super();
		this.movieService = movieService;
	}
	
	//​ផ្នែក​ create 
	@PostMapping("/add-movie")
	public ResponseEntity<MovieDto> addMovieHandler(
				@RequestPart MultipartFile file,
				@RequestPart String movieDto
			)throws IOException, EmptyFileException{
				if(file.isEmpty()) {
					throw new EmptyFileException("File is empty! Please send another file!");
				}
				MovieDto dto = convertToMovieDto(movieDto);
				return new ResponseEntity<>(movieService.addMovie(dto, file), HttpStatus.CREATED);
	}
	
	
	//​ផ្នែក​ Upate
	@PutMapping("/update/{movieId}")
	public ResponseEntity<MovieDto> updateMovieHandler(
				@PathVariable Integer movieId,
				@RequestPart MultipartFile file,
				@RequestPart String movieDtoObj
			) throws IOException {
		if (file.isEmpty()) file = null;
		MovieDto movieDto = convertToMovieDto(movieDtoObj);
		return ResponseEntity.ok(movieService.updateMovie(movieId, movieDto, file));
	}
	
	//​ផ្នែក​ convertToMovieDto
	private MovieDto convertToMovieDto(String movieDtoObj) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.readValue(movieDtoObj, MovieDto.class);
	}
	
	
	//​ផ្នែក​ delete
	@DeleteMapping("/delete/{movieId}")
	public ResponseEntity<String> deleteMovieHandler(@PathVariable Integer movieId) throws IOException {
		return ResponseEntity.ok(movieService.deleteMovie(movieId));
	}
	
	
	//​ផ្នែក​ get by id
	@GetMapping("/{movieId}")
	public ResponseEntity<MovieDto> getMovieHandler(@PathVariable Integer movieId){
		return ResponseEntity.ok(movieService.getMovie(movieId));
	}
	
	
	//​ផ្នែក​ get all
	@GetMapping("/all")
	public ResponseEntity<List<MovieDto>> getAllMoviesHandler(){
		return ResponseEntity.ok(movieService.getAllMovies());
	}
	
	
	@GetMapping("/allMoviesPage")
	public ResponseEntity<MoviePageResponse> getMoiesWithPagination(
				@RequestParam(defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
				@RequestParam(defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize
			){
		return ResponseEntity.ok(movieService.getAllMoviesWithPagination(pageNumber, pageSize));
	}
	
	@GetMapping("/allMoviesPageSort")
	public ResponseEntity<MoviePageResponse> getMoiesWithPaginationAndSorting(
				@RequestParam(defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
				@RequestParam(defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
				@RequestParam(defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
				@RequestParam(defaultValue = AppConstants.SORT_DIR, required = false) String dir
			){
		return ResponseEntity.ok(movieService.getAllMoviesWithPaginationAndSorting(pageNumber, pageSize, sortBy, dir));
	}
	
	
}
