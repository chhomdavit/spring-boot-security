package com.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminUserController {

	@GetMapping("/user/add")
	public String userAdd() {
		return "admin/user_add";
	}
	
	@GetMapping({"/user","/user/list"})
	public String userList() {
		return "admin/user_list";
	}
}
