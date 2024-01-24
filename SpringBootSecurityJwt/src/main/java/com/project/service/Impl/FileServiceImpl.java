package com.project.service.Impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.project.service.FileService;

@Service
public class FileServiceImpl implements FileService{

	@Override
	public String uploadFile(String path, MultipartFile file) throws IOException {
		// TODO Auto-generated method stub
		
		String filename = file.getOriginalFilename();

        String uniqueId = UUID.randomUUID().toString();
        String uniqueFileName = uniqueId.concat(filename.substring(filename.lastIndexOf(".")));
        String fullPath = path+ File.separator+uniqueFileName;

        File file1 = new File(path);
        if(!file1.exists()){
            file1.mkdir();
        }

        Files.copy(file.getInputStream(), Paths.get(fullPath));

        return uniqueFileName;
	}
	
	
	@Override
	public String uploadBase64Image(String path, String base64Image) throws IOException {
		// Generate a unique filename for the base64 image
        String uniqueId = UUID.randomUUID().toString();
        String uniqueFileName = uniqueId + ".png"; // Assume PNG format for simplicity
        String fullPath = path + File.separator + uniqueFileName;

        File file1 = new File(path);
        if (!file1.exists()) {
            file1.mkdir();
        }

        // Decode the base64 image and save it to the file system
        byte[] imageBytes = Base64.getDecoder().decode(base64Image);
        Files.write(Paths.get(fullPath), imageBytes);

        return uniqueFileName;
	}
	
	
	@Override
	public InputStream getResourceFile(String path, String fileName) throws FileNotFoundException {
		// TODO Auto-generated method stub
		
		String filePath = path + File.separator + fileName;
		return new FileInputStream(filePath);
	}

}
