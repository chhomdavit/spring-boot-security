package com.project.service.Impl;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.OverlappingFileLockException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.project.service.StorageService;

@Service
public class StorageServiceImpl implements StorageService{

	@Override
	public String uploadFile(String path, MultipartFile file) throws IOException {
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
	public InputStream getResourceFile(String path, String fileName) throws FileNotFoundException {
		String filePath = path + File.separator + fileName;
		return new FileInputStream(filePath);
	}
	
    
	@Override
    public String uploadBase64Image(String path, byte[] base64Image) throws IOException {
        String uniqueId = UUID.randomUUID().toString();
        String uniqueFileName = uniqueId + ".png"; 
        String fullPath = path + File.separator + uniqueFileName;

        Files.createDirectories(Paths.get(path)); 
        Files.write(Paths.get(fullPath), base64Image);

        return uniqueFileName;
    }


	@Override
	public void deleteFile(String filePath) throws IOException {
		File file = new File(filePath);
		if(file.exists() && !file.isDirectory()) {
			RandomAccessFile raf = new RandomAccessFile(file, "rw");
			FileChannel channel = raf.getChannel();
			FileLock lock = null;
			try {
				lock = channel.tryLock();
			} catch (OverlappingFileLockException e) {
				// File is open by another thread
			}
			if(lock != null) {
				lock.release();
				channel.close();
				raf.close();
				Files.delete(Paths.get(filePath));
			}
		}
	}
	
}
