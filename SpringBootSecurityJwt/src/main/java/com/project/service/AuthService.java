package com.project.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.dto.AuthResponse;
import com.project.dto.LoginRequest;
import com.project.dto.RegisterRequest;
import com.project.dto.UserResponse;
import com.project.model.User;
import com.project.model.UserRole;
import com.project.repository.UserRepository;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class AuthService {
	
	private final PasswordEncoder passwordEncoder;
	private final UserRepository userRepository;
	private final JwtService jwtService;
	private final RefreshTokenService refreshTokenService;
	private final AuthenticationManager authenticationManager;
	
	
	public AuthResponse register(RegisterRequest registerRequest) {
		var user = User.builder()
				.name(registerRequest.getName())
				.email(registerRequest.getEmail())
				.username(registerRequest.getUsername())
				.password(passwordEncoder.encode(registerRequest.getPassword()))
				.role(UserRole.USER)
				.build();
		
		User savedUser = userRepository.save(user);
		var  accessToken = jwtService.generateToken(savedUser);
		var  refreshToken = refreshTokenService.createRefreshToken(savedUser.getEmail());
		
		return AuthResponse.builder()
				.user(UserResponse.builder()
                        .name(user.getName())
                        .email(user.getEmail())
                        .build())
				.accessToken(accessToken)
				.refreshToken(refreshToken.getRefreshToken())
				.build();
	}
	
	
//	public AuthResponse login(LoginRequest loginRequest) {
//		authenticationManager.authenticate(
//					new UsernamePasswordAuthenticationToken(
//							loginRequest.getEmail(),
//							loginRequest.getPassword()
//							)
//				);
//		var user = userRepository.findByEmail(loginRequest.getEmail()).orElseThrow(()-> new UsernameNotFoundException("User not found !"));
//		var accessToken = jwtService.generateToken(user);
//		var refreshToken = refreshTokenService.createRefreshToken(loginRequest.getEmail());
//		
//		return AuthResponse.builder()
//				.accessToken(accessToken)
//				.refreshToken(refreshToken.getRefreshToken())
//				.build();
//		
//	}
	
	public AuthResponse login(LoginRequest loginRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );
        var user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found!"));
        var accessToken = jwtService.generateToken(user);
        var refreshToken = refreshTokenService.createRefreshToken(loginRequest.getEmail());

        return AuthResponse.builder()
                .user(UserResponse.builder()
                        .name(user.getName())
                        .email(user.getEmail())
                        .build())
                .accessToken(accessToken)
                .refreshToken(refreshToken.getRefreshToken())
                .build();
    }
	
	
}
