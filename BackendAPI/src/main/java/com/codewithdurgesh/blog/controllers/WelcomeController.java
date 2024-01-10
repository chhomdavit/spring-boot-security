package com.codewithdurgesh.blog.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/welcome")
public class WelcomeController {
	@GetMapping("/")
	public String welcomeMsg() {
		return "Hi, this application is developed by Parshuram";
	}
}
