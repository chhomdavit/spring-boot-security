package com.project.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.model.Photo;

public interface PhotoRepository extends JpaRepository<Photo, Long>{

	Optional<Photo> findFirstById(Long id);
}
