package com.project.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.model.AdminEmployee;

@Repository
public interface AdminEmployeeRepository extends JpaRepository<AdminEmployee, Long>{

	//page seacher model ត្រូវតែកែវាចាប់​ពី Repository មក
	Page<AdminEmployee> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseOrEmailContainingIgnoreCase(String firstName, String lastName, String email, Pageable pageable);

}