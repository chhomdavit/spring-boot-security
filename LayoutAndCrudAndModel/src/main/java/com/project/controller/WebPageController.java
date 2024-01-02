package com.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebPageController {
   
	@GetMapping("/index")
	 public String HomePage() {
		return "index";
	}
	
	@GetMapping("/about")
	 public String AboutPage() {
		return "about";
	}
	
	@GetMapping("/contact")
	 public String ContactPage() {
		return "contact";
	}
	
}
