package com.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
@CrossOrigin("*")
public class SpringBootSecurityJwt1Application {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootSecurityJwt1Application.class, args);
	}

}
