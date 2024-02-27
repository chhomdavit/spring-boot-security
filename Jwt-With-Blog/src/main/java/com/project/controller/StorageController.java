package com.project.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.project.service.StorageService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/v1/auth/file")
public class StorageController {

	@Autowired
	private StorageService storageService;
	
	@Value("${project.upload}")
	private String path;
	
	
	@PostMapping("/upload")
	public ResponseEntity<String> uploadFileHandler(@RequestPart MultipartFile file) throws IOException{
		String uploadedFileName = storageService.uploadFile(path, file);
		return ResponseEntity.ok("File upload :" + uploadedFileName);
	}
	
	
	@GetMapping("/{fileName}")
	public void serveFileHandler(@PathVariable String fileName, HttpServletResponse httpServletResponse) throws IOException {
		InputStream resourceFile = storageService.getResourceFile(path, fileName);
		httpServletResponse.setContentType(MediaType.IMAGE_PNG_VALUE);
	    StreamUtils.copy(resourceFile, httpServletResponse.getOutputStream());
	}
	
	
	
	@PostMapping("/upload/base64")
	public ResponseEntity<String> uploadBase64ImageHandler(@RequestBody String base64Image) throws IOException {
	        
		    // Ensure base64Image is not null or empty
	        if (base64Image == null || base64Image.isEmpty()) {
	            return ResponseEntity.badRequest().body("Invalid base64 image data");
	        }

	        // Check for metadata and handle it appropriately
	        String base64ImageData = base64Image; // Assume no metadata for now
	        if (base64Image.contains(",")) {
	            base64ImageData = base64Image.split(",")[1];
	        }

	        // Decode the base64 image
	        byte[] decodedImage = Base64.getMimeDecoder().decode(base64ImageData); // Use MIME decoder
	        String uploadedFileName = storageService.uploadBase64Image(path, decodedImage);
	        return ResponseEntity.ok("Base64 image upload: " + uploadedFileName);
	 } 
	
	

	
}
