package com.project.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.model.RefreshToken;
import com.project.model.User;
import com.project.service.AuthService;
import com.project.service.JwtService;
import com.project.service.RefreshTokenService;
import com.project.util.AuthResponse;
import com.project.util.LoginRequest;
import com.project.util.RefreshTokenRequest;
import com.project.util.RegisterRequest;

@RestController
@RequestMapping("/api/v1/auth/")
public class AuthController {
	
	private final AuthService authService;
	private final RefreshTokenService refreshTokenService;
	private final JwtService jwtService;
	public AuthController(AuthService authService, RefreshTokenService refreshTokenService, JwtService jwtService) {
		super();
		this.authService = authService;
		this.refreshTokenService = refreshTokenService;
		this.jwtService = jwtService;
	}

	@PostMapping("/register")
	public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest registerRequest){
		return ResponseEntity.ok(authService.register(registerRequest));
	}
	
	@PostMapping("/login")
	public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest){
		return ResponseEntity.ok(authService.login(loginRequest));
	}
	
	@PostMapping("/refresh")
	public ResponseEntity<AuthResponse> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest){
		RefreshToken refreshToken = refreshTokenService.verifyRefreshToken(refreshTokenRequest.getRefreshToken());
		User user = refreshToken.getUser();
		
		String accessToken = jwtService.generateToken(user);
		
		return ResponseEntity.ok(AuthResponse.builder()
					.accessToken(accessToken)
					.refreshToken(refreshToken.getRefreshToken())
					.build()
				);
		
	}
	
}
