package com.project.service;


import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

	private static String SECRET_KEY = "45979596604060769E0DS3J56J3F0SG8D68S0FG0DS348395DDG9449333855D4K6J0DOE539CCS";

	//extract username from JWT
	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}
	
	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}
	
	
	//extract information form JWT
	private  Claims extractAllClaims(String token) {
		
		return Jwts
				.parserBuilder()
				.setSigningKey(getSingnInKey())
				.build()
				.parseClaimsJws(token)
				.getBody();
	}
	
	//decode and get the key
	private Key getSingnInKey() {
		// decode SECRET_KEY
		byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
		return Keys.hmacShaKeyFor(keyBytes);
	}
	
	
	public String generateToken(UserDetails userDetails) {
		return generateToken(new HashMap<>(), userDetails);
		
	}
	
	//generate token using Jwt utility class and return token as String
	public String generateToken(
			Map<String, Object> extraClaims,
			UserDetails userDetails
			) {
		return Jwts
				.builder()
				.setClaims(extraClaims)
				.setSubject(userDetails.getUsername())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 25 * 1000))
				.signWith(getSingnInKey(), SignatureAlgorithm.HS256)
				.compact();
	}
	
	//if token is valid by checking if token is expired for current user
	public boolean isTokenValid(String token, UserDetails userDetails) {
		final String username = extractUsername(token);
		return (username.equals(userDetails.getUsername()) && !isTokeExpired(token));
	}
	
	//if token is expired
	private boolean isTokeExpired(String token) {
		return extractExpiration(token).before(new Date());
	}
	
	//get expiration date from token
	private Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}
	
}
