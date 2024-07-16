package com.project.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.project.model.AdminEmployee;
import com.project.service.AdminEmployeeService;

@Controller
@RequestMapping("/admin")
public class AdminEmployeeController {
	
	@Autowired
	private AdminEmployeeService adminEmployeeService;

//	@GetMapping({"/employee","/employee/list"})
//	public String employeeList(Model model) {
//		List<AdminEmployee> list_employees = adminEmployeeService.getAllAdminEmployees();
//		model.addAttribute("list_employees", list_employees);
//		return "admin/employee/employee_list";
//	}
	
	
	@GetMapping({"/employee","/employee/list"})
	public String employeeList(@RequestParam(defaultValue = "0", required = false) Integer page,
	                           @RequestParam(defaultValue = "2", required = false) Integer size,
	                           @RequestParam(required = false) String keyword,
	                           Model model) {

	    Page<AdminEmployee> pageResult;

	    if (keyword != null && !keyword.isEmpty()) {
	        pageResult = adminEmployeeService.searchAdminEmployees(keyword, page, size);
	    } else {
	        pageResult = adminEmployeeService.getAllAdminEmployees(page, size);
	    }

	    List<AdminEmployee> list_employees = pageResult.getContent();

	    model.addAttribute("list_employees", list_employees);
	    model.addAttribute("currentPage", pageResult.getNumber());
	    model.addAttribute("totalPages", pageResult.getTotalPages());
	    model.addAttribute("pageSize", size);
	    model.addAttribute("keyword", keyword);

	    return "admin/employee/employee_list";
	}
	
	
	
	
	@GetMapping("/employee/create")
	public String employeeCreate(Model model) {
		AdminEmployee adminEmployee = new AdminEmployee();
		model.addAttribute("adminEmployee", adminEmployee);
		return "admin/employee/employee_create";
	}
	
	@GetMapping("/employee/update/{id}")
	public String employeeUpdate(@PathVariable(value = "id") long id, Model model) {
			AdminEmployee adminEmployee = adminEmployeeService.getAdminEmployeeById(id);
			model.addAttribute("adminEmployee", adminEmployee);
		return "admin/employee/employee_update";
	}
	

//	@PostMapping("/save")
//	public String save(
//	        @RequestParam("firstName") String firstName,
//	        @RequestParam("lastName") String lastName,
//	        @RequestParam("email") String email,
//	        @RequestParam("photo") MultipartFile photo,
//	        ModelMap modelMap
//	) {
//	    AdminEmployee adminEmployee = new AdminEmployee();
//	    adminEmployee.setFirstName(firstName);
//	    adminEmployee.setLastName(lastName);
//	    adminEmployee.setEmail(email);
//	    if (photo.isEmpty()) {
//	        return "admin/employee/employee_list";
//	    }
//
//	    String uploadDirectory = "uploads/";
//	    File uploadDir = new File(uploadDirectory);
//	    if (!uploadDir.exists()) {
//	        uploadDir.mkdir();
//	    }
//
//	    try {
//	        InputStream photoInputStream = photo.getInputStream();
//	        Files.copy(
//	                photoInputStream,
//	                Paths.get(uploadDirectory,
//	                        photo.getOriginalFilename()),
//	                StandardCopyOption.REPLACE_EXISTING
//	        );
//	        adminEmployee.setPhoto(photo.getOriginalFilename().toLowerCase());
//	    } catch (Exception e) {
//	        e.printStackTrace();
//	        return "error";
//	    }
//	    
//	    adminEmployeeService.create(adminEmployee);
//	    return "redirect:/admin/employee/list";
//	}
	
	@PostMapping({"/save", "/update/{id}"})
	public String save(
	        @PathVariable(value = "id", required = false) Long id,
	        @RequestParam("firstName") String firstName,
	        @RequestParam("lastName") String lastName,
	        @RequestParam("email") String email,
	        @RequestParam(value = "photo", required = false) MultipartFile photo,
	        ModelMap modelMap
	) {
	    AdminEmployee adminEmployee = new AdminEmployee();
	    adminEmployee.setFirstName(firstName);
	    adminEmployee.setLastName(lastName);
	    adminEmployee.setEmail(email);

	    if (id != null) {
	        AdminEmployee existingEmployee = adminEmployeeService.getAdminEmployeeById(id);
	        if (existingEmployee != null) {
	            adminEmployee.setPhoto(existingEmployee.getPhoto());
	        }
	    }

	    if (photo != null && !photo.isEmpty()) {
	        String uploadDirectory = "uploads/";
	        File uploadDir = new File(uploadDirectory);
	        if (!uploadDir.exists()) {
	            uploadDir.mkdir();
	        }

	        try {
	        	
	        	if (adminEmployee.getPhoto() != null && !adminEmployee.getPhoto().isEmpty()) {
	                Files.deleteIfExists(Paths.get(uploadDirectory, adminEmployee.getPhoto()));
	            }
	        	
	            InputStream inputStream = photo.getInputStream();
	            String newFileName = photo.getOriginalFilename().toLowerCase();
	            Files.copy(
	            		inputStream,
	                    Paths.get(uploadDirectory, newFileName),
	                    StandardCopyOption.REPLACE_EXISTING
	            );
	            adminEmployee.setPhoto(newFileName);
	        } catch (Exception e) {
	            e.printStackTrace();
	            return "error";
	        }
	    }

	    if (id != null) {
	        adminEmployee.setId(id);
	        adminEmployeeService.update(adminEmployee);
	    } else {
	        adminEmployeeService.create(adminEmployee);
	    }

	    return "redirect:/admin/employee/list";
	}

	
	@GetMapping("/employee/delete/{id}")
	public String delete(@PathVariable(value = "id") long id) {

	    AdminEmployee adminEmployee = adminEmployeeService.getAdminEmployeeById(id);

	    if (adminEmployee != null) {
	        String uploadDirectory = "uploads/";
	        String photoFileName = adminEmployee.getPhoto();
	        if (photoFileName != null && !photoFileName.isEmpty()) {
	            try {
	                Files.deleteIfExists(Paths.get(uploadDirectory, photoFileName));
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }

	        adminEmployeeService.delete(id);
	    }

	    return "redirect:/admin/employee/list";
	}
		
}
