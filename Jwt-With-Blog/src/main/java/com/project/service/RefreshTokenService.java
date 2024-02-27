package com.project.service;

import java.time.Instant;
import java.util.UUID;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.project.model.RefreshToken;
import com.project.model.User;
import com.project.repository.RefreshTokenRepository;
import com.project.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

	private final UserRepository userRepository;
	private final RefreshTokenRepository refreshTokenRepository;
	
	
//	public RefreshToken createRefreshToken(String username) {
//		User user = userRepository.findByEmail(username)
//				.orElseThrow(()->new UsernameNotFoundException("User not found with email" + username));
//		 RefreshToken refreshToken = user.getRefreshToken();
//		 
//		 if(refreshToken == null) {
//			 Long refreshTokenValidtity = (long) (30*1000); 
//			 refreshToken = RefreshToken.builder()
//					 .refreshToken(UUID.randomUUID().toString())
//					 .expirationTime(Instant.now().plusMillis(refreshTokenValidtity))
//					 .user(user)
//					 .build();
//		 }
//		 return refreshToken;
//	}
	
	public RefreshToken createRefreshToken(String username) {
	    User user = userRepository.findByEmail(username)
	            .orElseThrow(() -> new UsernameNotFoundException("User not found with email" + username));

	    RefreshToken refreshToken = user.getRefreshToken();

	    if (refreshToken == null) {
	        Long refreshTokenValidity = (long) (30 * 24 * 60 * 60); // 30 days validity in seconds
	        refreshToken = RefreshToken.builder()
	                .refreshToken(UUID.randomUUID().toString())
	                .expirationTime(Instant.now().plusSeconds(refreshTokenValidity))
	                .user(user)
	                .build();

	        // Save the new refresh token to the database
	        refreshTokenRepository.save(refreshToken);

	        // Update the user's refresh token
	        user.setRefreshToken(refreshToken);
	        userRepository.save(user);
	    }

	    return refreshToken;
	}
	
	
	public RefreshToken verifyRefreshToken(String refreshToken) {
	  RefreshToken refToken	= refreshTokenRepository.findByRefreshToken(refreshToken)
			  .orElseThrow(()-> new RuntimeException("Refrsh token not found"));
	  
	  if(refToken.getExpirationTime().compareTo(Instant.now()) < 0 ) {
		  refreshTokenRepository.delete(refToken);
		  throw new RuntimeException("Refresh Token expired");
	  }
	  return refToken;
	}
	
}
