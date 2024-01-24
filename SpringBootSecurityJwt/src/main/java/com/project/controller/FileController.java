package com.project.controller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.project.service.FileService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/file/")
@CrossOrigin(origins = "*")
public class FileController {

	private final FileService fileService;
	
	@Value("${project.poster}")
	private String path;
	
	public FileController(FileService fileService) {
		super();
		this.fileService = fileService;
	}
	
	

	@PostMapping("/upload")
	public ResponseEntity<String> uploadFileHandler(@RequestPart MultipartFile file) throws IOException{
		String uploadedFileName = fileService.uploadFile(path, file);
		return ResponseEntity.ok("File upload :" + uploadedFileName);
	}
	
	@PostMapping("/upload/base64")
    public ResponseEntity<String> uploadBase64ImageHandler(@RequestBody String base64Image) throws IOException {
        // Use the URL-safe encoder to handle any illegal characters
        String encodedImage = Base64.getUrlEncoder().encodeToString(base64Image.getBytes(StandardCharsets.UTF_8));
        String uploadedFileName = fileService.uploadBase64Image(path, encodedImage);
        return ResponseEntity.ok("Base64 image upload: " + uploadedFileName);
    }
	
	@GetMapping("/{fileName}")
	public void serveFileHandler(@PathVariable String fileName, HttpServletResponse httpServletResponse) throws IOException {
		InputStream resourceFile = fileService.getResourceFile(path, fileName);
		httpServletResponse.setContentType(MediaType.IMAGE_PNG_VALUE);
	    StreamUtils.copy(resourceFile, httpServletResponse.getOutputStream());
	}
}
