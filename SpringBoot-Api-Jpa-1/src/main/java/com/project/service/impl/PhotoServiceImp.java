package com.project.service.impl;

import java.util.Objects;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.project.dto.PhotoRequestDto;
import com.project.model.Photo;
import com.project.repository.PhotoRepository;
import com.project.service.PhotoService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class PhotoServiceImp implements PhotoService{
	
	private final PhotoRepository photoRepository;
	private final ModelMapper modelMapper;
	
	
	@Override
	public void addNewPhoto(PhotoRequestDto photoRequestDto) throws Exception {
		Photo photo = photoRepository.save(modelMapper.map(photoRequestDto, Photo.class));
        if(Objects.isNull(photo.getId())) {
            log.error("Saving new photo was failed!");
            throw new Exception();
        }
		
	}
	@Override
	public Photo checkById(Long id) {
		Optional<Photo> photo = photoRepository.findFirstById(id);
        return photo.orElse(null);
	}


}
