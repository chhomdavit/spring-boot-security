package com.codewithdurgesh.blog.security;

import java.util.Collection;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.codewithdurgesh.blog.entities.User;
import com.codewithdurgesh.blog.exceptions.ResourceNotFoundException;
import com.codewithdurgesh.blog.repositories.UserRepo;
@Service
public class CustomUserDetailService implements UserDetailsService {
	@Autowired
	private UserRepo userRepo;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user=this.userRepo.findByEmail(username)
				.orElseThrow(()-> new ResourceNotFoundException("User", "email :"+username, 0));
		return user;
	}

	

}
