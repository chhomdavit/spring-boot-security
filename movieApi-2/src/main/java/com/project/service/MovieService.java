package com.project.service;

import java.io.IOException;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;
import com.project.dto.MovieDto;
import com.project.dto.MoviePageResponse;


public interface MovieService {

	MovieDto addMovie(MovieDto movieDto, MultipartFile file) throws IOException;
	
	MovieDto getMovie(Integer movieId);
	
	List<MovieDto> getAllMovies();
	
	MovieDto updateMovie(Integer movieId, MovieDto movieDto, MultipartFile file) throws IOException;
	
	String deleteMovie(Integer movieId) throws IOException;
	
	MoviePageResponse getAllMoviesWithPagination(Integer pageNumber, Integer pageSize);
	
	MoviePageResponse getAllMoviesWithPaginationAndSorting( Integer pageNumber, Integer pageSize,
														   String sortBy, String dir);

	MoviePageResponse searchMovies(String keyword, Integer pageNumber, Integer pageSize);

}
