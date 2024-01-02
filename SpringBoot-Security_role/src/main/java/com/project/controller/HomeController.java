package com.project.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.project.model.User;
import com.project.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {

	@Autowired
	private UserService userService;
	
	@GetMapping("/")
	public String index() {
		return "index";
	}

	@GetMapping("/register")
	public String register() {
		return "register";
	}
	
	@GetMapping("/signin")
	public String login() {
		return "login";
	}
	
	@GetMapping("/user-logout")
	public String logout() {
		return "redirect:/login";
	}
	

	@PostMapping("/saveUser")
	public String saveUser(@ModelAttribute User user, HttpSession session, Model model) {
		User u = userService.saveUser(user);
		if (u != null) {
			session.setAttribute("msg", "Register successfully");

		} else {
			session.setAttribute("msg", "Something wrong server");
		}
		return "redirect:/register";
	}
}
