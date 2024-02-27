package com.project.service;

import com.project.dto.AuthResponse;
import com.project.dto.LoginRequest;
import com.project.dto.RegisterRequest;

public interface AuthService {

	AuthResponse register(RegisterRequest registerRequest);
	
    AuthResponse login(LoginRequest loginRequest);
    
}
