package com.project.service;

import com.project.dto.PhotoRequestDto;
import com.project.model.Photo;

public interface PhotoService {

	void addNewPhoto(PhotoRequestDto photoRequestDto) throws Exception;

    Photo checkById(Long id);
}
