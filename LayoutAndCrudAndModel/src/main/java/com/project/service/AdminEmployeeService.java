package com.project.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.project.model.AdminEmployee;

public interface AdminEmployeeService {

	List<AdminEmployee> getAllAdminEmployees();
	void create(AdminEmployee adminEmployee);
	void update(AdminEmployee adminEmployee);
	void delete(Long id);
	AdminEmployee getAdminEmployeeById(Long id);
	
	Page<AdminEmployee> getAllAdminEmployees(int page, int size);
	Page<AdminEmployee> searchAdminEmployees(String keyword, int page, int size);

}
