package com.codewithdurgesh.blog.services;

import java.util.List;

import com.codewithdurgesh.blog.payloads.UserDto;

public interface UserService {
	UserDto registerNewUser(UserDto userDto);
	UserDto createUser(UserDto user);
	UserDto updateUser(UserDto user, Integer id);
	UserDto getUserById(Integer id);
	 List<UserDto> getAllUsers();
	 void deleteUser(Integer id);
	
}
