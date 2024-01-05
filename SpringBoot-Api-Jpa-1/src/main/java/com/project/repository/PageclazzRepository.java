package com.project.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


import com.project.model.PageClazz;

public interface PageclazzRepository extends JpaRepository<PageClazz, Long>{

	
	Optional<PageClazz> findFirstById(Long id);
}
