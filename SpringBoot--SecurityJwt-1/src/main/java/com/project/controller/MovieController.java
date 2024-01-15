package com.project.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class MovieController {

	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping("/get-admin")
	public String GetMovieAdmin() {
		return "This is admin";
	}

	@PreAuthorize("hasAuthority('USER')")
	@GetMapping("/get-user")
	public String GetMovieUser() {
		return "This is user";
	}
}
