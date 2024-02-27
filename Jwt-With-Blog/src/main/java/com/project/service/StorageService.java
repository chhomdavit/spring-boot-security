package com.project.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;


import org.springframework.web.multipart.MultipartFile;

public interface StorageService {

//	public String storeFile(MultipartFile file);
//	InputStream getResourceFile(String fileName) throws FileNotFoundException;
//    public Stream<Path> loadAll(); 
//    public byte[] readFileContent(String fileName);
//    public void deleteAllFiles();
//    public void deleteByFilename(String fileName);
//    Path getStorageFolder();
	
	    String uploadFile(String path, MultipartFile file) throws IOException;
	    
	    String uploadBase64Image(String path, byte[] base64Image) throws IOException;
		
		InputStream getResourceFile(String path, String fileName) throws FileNotFoundException;

		void deleteFile(String filePath) throws IOException;
}
