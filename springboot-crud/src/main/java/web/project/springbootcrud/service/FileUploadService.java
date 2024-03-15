package web.project.springbootcrud.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public interface FileUploadService {
    
    byte[] getImage(String photo);

    String saveFile(MultipartFile file, String uploadDirectory) throws IOException;
}
