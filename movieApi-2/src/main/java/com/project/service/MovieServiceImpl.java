package com.project.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.project.dto.MovieDto;
import com.project.dto.MoviePageResponse;
import com.project.exception.FileExistsException;
import com.project.exception.MovieNotFoundException;
import com.project.model.Movie;
import com.project.repository.MovieRepository;

@Service
public class MovieServiceImpl implements MovieService{

	private final MovieRepository movieRepository;
	private final FileService fileService;
	private String path;
	private String baseUrl;
	private final ModelMapper modelMapper;
	
	public MovieServiceImpl(MovieRepository movieRepository, FileService fileService, @Value("${project.poster}") String path, @Value("${base.url}") String baseUrl, ModelMapper modelMapper) {
		super();
		this.movieRepository = movieRepository;
		this.fileService = fileService;
		this.path = path;
		this.baseUrl = baseUrl;
		this.modelMapper = modelMapper;
	}
	
	//​ផ្នែក​ create 
	@Override
	public MovieDto addMovie(MovieDto movieDto, MultipartFile file) throws IOException {
	    // 1. upload file
	    if (Files.exists(Paths.get(path + File.separator + file.getOriginalFilename()))) {
	        throw new FileExistsException("File already exists! Please enter another file name");
	    }
	    String uploadFileName = fileService.uploadFile(path, file);

	    // 2. set the value of field 'poster' as filename
	    movieDto.setPoster(uploadFileName);

	    // 3. map to movie object
	    Movie movie = modelMapper.map(movieDto, Movie.class);

	    // 4. save the movie object -> saved Movie object
	    Movie savedMovie = movieRepository.save(movie);

	    // 5. generate the posterUrl
	    String posterUrl = baseUrl + "/file/" + uploadFileName;

	    // 6. map movie object to dto object and return it
	    MovieDto response = modelMapper.map(savedMovie, MovieDto.class);
	    response.setPosterUrl(posterUrl);

	    return response;
	}
	
	
	
	
	//​ផ្នែក​ get by id
	@Override
	public MovieDto getMovie(Integer movieId) {
		// 1.check the data in DB and if exists fetch the date of given ID
		Movie movie = movieRepository.findById(movieId)
				.orElseThrow(()-> new MovieNotFoundException("Movie not found with id" + movieId));
		
		// 2. map to MovieDto object using ModelMapper
        MovieDto response = modelMapper.map(movie, MovieDto.class);

        // 3. generate posterUrl
        response.setPosterUrl(baseUrl + "/file/" + movie.getPoster());

        return response;
	}
	
	//​ផ្នែក​ get All
	public List<MovieDto> getAllMovies() {
        // 1. fetch all data from DB
        List<Movie> movies = movieRepository.findAll();

        // 2. map the list of Movie objects to a list of MovieDto objects using ModelMapper
        List<MovieDto> movieDtos = movies.stream()
                .map(movie -> {
                    // map each Movie object to MovieDto and set posterUrl
                    MovieDto movieDto = modelMapper.map(movie, MovieDto.class);
                    movieDto.setPosterUrl(baseUrl + "/file/" + movie.getPoster());
                    return movieDto;
                })
                .collect(Collectors.toList());

        return movieDtos;
     }

	
	//​ផ្នែក​ update
	@Override
    public MovieDto updateMovie(Integer movieId, MovieDto movieDto, MultipartFile file) throws IOException {

        // 1. Check if the movie object exists with the given movieId
        Movie movieIdCheck = movieRepository.findById(movieId)
                .orElseThrow(() -> new MovieNotFoundException("Movie not found with id" + movieId));

        // 2. If the file is not null, delete the existing file associated with the record and upload the new file
        String fileName = movieIdCheck.getPoster();
        if (file != null) {
            Files.deleteIfExists(Paths.get(path + File.separator + fileName));
            fileName = fileService.uploadFile(path, file);
        }

        // 3. Set the movieDto's poster value according to step 2
        movieDto.setPoster(fileName);

        // 4. Map movieDto to a movie object using ModelMapper
        Movie updatedMovie = modelMapper.map(movieDto, Movie.class);
        updatedMovie.setMovieId(movieIdCheck.getMovieId()); // Set the existing movieId

        // 5. Save the updated movie object
        Movie savedMovie = movieRepository.save(updatedMovie);

        // 6. Generate the posterUrl
        String posterUrl = baseUrl + "/file/" + fileName;

        // 7. Map the savedMovie to a MovieDto and return it
        MovieDto response = modelMapper.map(savedMovie, MovieDto.class);
        response.setPosterUrl(posterUrl);

        return response;
    }

	
	//​ផ្នែក​ delete
	@Override
	public String deleteMovie(Integer movieId) throws IOException {
		
		//1. check if movie object exists in DB
		Movie movieIdCheck = movieRepository.findById(movieId)
				.orElseThrow(()-> new MovieNotFoundException("Movie not found with id" + movieId));
		Integer id = movieIdCheck.getMovieId();	
		
		//2. delete  the file associated with this  obj
		Files.deleteIfExists(Paths.get(path + File.separator + movieIdCheck.getPoster()));
		
		//3. delete the movie object
		movieRepository.delete(movieIdCheck);
		
		return "Movie deleted with id = " + id;
	}

	
	//​ផ្នែក​ pagination
	@Override
	public MoviePageResponse getAllMoviesWithPagination(Integer pageNumber, Integer pageSize) {
		
		Pageable pageable = PageRequest.of(pageNumber, pageSize);
		
		Page<Movie> moviePages = movieRepository.findAll(pageable);
		List<Movie> movies = moviePages.getContent();
		List<MovieDto> movieDtos = new ArrayList<>();

		for(Movie movie: movies) {
			String posterUrl = baseUrl + "/file/" + movie.getPoster(); 
			MovieDto movieDto = modelMapper.map(movie, MovieDto.class);
			movieDto.setPosterUrl(posterUrl);
			movieDtos.add(movieDto);
		}
		return new MoviePageResponse( movieDtos, pageNumber, pageSize, (long) moviePages.getTotalElements(),
				 				        moviePages.getTotalPages(),moviePages.isLast()); 
	 }

	//​ផ្នែក​ pagination and sort
	 @Override
	    public MoviePageResponse getAllMoviesWithPaginationAndSorting(Integer pageNumber, Integer pageSize, String sortBy, String dir) {
	        
		 Sort sort = dir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
	        
		 Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
	       
		 Page<Movie> moviePages = movieRepository.findAll(pageable);

	        
		 List<MovieDto> movieDtos = moviePages.getContent().stream().map(movie -> {
	            MovieDto movieDto = modelMapper.map(movie, MovieDto.class);
	            movieDto.setPosterUrl(baseUrl + "/file/" + movie.getPoster());
	            return movieDto;
	        }).collect(Collectors.toList());

	        
		 return new MoviePageResponse(
				 movieDtos, pageNumber, pageSize,moviePages.getTotalElements(), 
				 moviePages.getTotalPages(), moviePages.isLast());
	    }

	@Override
	public MoviePageResponse searchMovies(String keyword, Integer pageNumber, Integer pageSize) {
		 Pageable pageable = PageRequest.of(pageNumber, pageSize);

	        Page<Movie> moviePages = movieRepository.findByTitleContainingIgnoreCase(keyword, pageable);

	        List<MovieDto> movieDtos = moviePages.getContent().stream().map(movie -> {
	            MovieDto movieDto = modelMapper.map(movie, MovieDto.class);
	            movieDto.setPosterUrl(baseUrl + "/file/" + movie.getPoster());
	            return movieDto;
	        }).collect(Collectors.toList());

	        return new MoviePageResponse(
	                movieDtos, pageNumber, pageSize, moviePages.getTotalElements(),
	                moviePages.getTotalPages(), moviePages.isLast());
	}

}
