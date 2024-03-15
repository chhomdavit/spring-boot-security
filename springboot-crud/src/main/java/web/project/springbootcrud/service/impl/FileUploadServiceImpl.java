package web.project.springbootcrud.service.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import web.project.springbootcrud.service.FileUploadService;

@Service
public class FileUploadServiceImpl implements FileUploadService{

    @Override
    public byte[] getImage(String photo) {
       try {
            Path filename = Paths.get("uploads", photo);
            return Files.readAllBytes(filename);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String saveFile(MultipartFile file, String uploadDirectory) throws IOException {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("File is empty");
        }

        File directory = new File(uploadDirectory);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        String originalFileName = file.getOriginalFilename();
        if (originalFileName == null) {
            throw new IllegalArgumentException("Original file name is null");
        }
        String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
        String newFileName = UUID.randomUUID().toString() + fileExtension;

        Path filePath = Paths.get(uploadDirectory, newFileName);
        Files.copy(file.getInputStream(), filePath);

        return newFileName;
    }

}