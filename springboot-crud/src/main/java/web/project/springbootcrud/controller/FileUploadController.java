package web.project.springbootcrud.controller;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import web.project.springbootcrud.service.FileUploadService;


@RestController
@RequestMapping("/api/images")
@RequiredArgsConstructor
public class FileUploadController {

   private final FileUploadService fileUploadService;

    

    @GetMapping("/{photo}")
    @ResponseBody
    public ResponseEntity<ByteArrayResource> getImage(@PathVariable("photo") String photo) {
        if (photo != null && !photo.isEmpty()) {
            byte[] buffer = fileUploadService.getImage(photo);
            if (buffer != null) {
                ByteArrayResource byteArrayResource = new ByteArrayResource(buffer);
                final MediaType image_PNG2 = MediaType.IMAGE_PNG;
				if (image_PNG2 != null) {
					return ResponseEntity.ok()
					        .contentLength(buffer.length)
					        .contentType(image_PNG2)
					        .body(byteArrayResource);
				} else {
					// TODO handle null value
					return null;
				}
            }
        }
        return ResponseEntity.badRequest().build();
    }
}