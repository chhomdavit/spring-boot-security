package com.project.service;

import java.util.List;
import java.util.Optional;

import javax.management.RuntimeErrorException;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.project.model.AdminEmployee;
import com.project.repository.AdminEmployeeRepository;

@Service
public class AdminEmployeeServiceImp implements AdminEmployeeService{

	@Autowired
	private AdminEmployeeRepository adminEmployeeRepository;
	
	@Override
	public List<AdminEmployee> getAllAdminEmployees() {
		// TODO Auto-generated method stub
		return (List<AdminEmployee>) adminEmployeeRepository.findAll();
	}

	@Override
	public void create(AdminEmployee adminEmployee) {
		// TODO Auto-generated method stub
		adminEmployeeRepository.save(adminEmployee);
		
	}

	@Override
	public void update(AdminEmployee adminEmployee) {
		// TODO Auto-generated method stub
		adminEmployeeRepository.save(adminEmployee);
		
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		adminEmployeeRepository.deleteById(id);
		
	}

	@Override
	public AdminEmployee getAdminEmployeeById(Long id) {
		// TODO Auto-generated method stub
		Optional<AdminEmployee> optional = adminEmployeeRepository.findById(id);
		AdminEmployee adminEmployee = null;
		if (optional.isPresent()) {
			adminEmployee = optional.get();
		} else {
			throw new RuntimeErrorException(null, "Eemployee not fount for :: " + id);
		}
		return adminEmployee;
	}

	//​ ផ្នែករបស់ pagination
	@Override
	public Page<AdminEmployee> getAllAdminEmployees(int page, int size) {
		// TODO Auto-generated method stub
		Pageable pageable = PageRequest.of(page, size);
        return adminEmployeeRepository.findAll(pageable);
	}

	//​ ផ្នែករបស់ seacher
	@Override
	public Page<AdminEmployee> searchAdminEmployees(String keyword, int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
	    return adminEmployeeRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseOrEmailContainingIgnoreCase(keyword, keyword, keyword, pageable);
	}

}
