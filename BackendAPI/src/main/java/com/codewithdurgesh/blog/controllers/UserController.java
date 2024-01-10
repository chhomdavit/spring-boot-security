package com.codewithdurgesh.blog.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codewithdurgesh.blog.payloads.ApiResponse;
import com.codewithdurgesh.blog.payloads.UserDto;
import com.codewithdurgesh.blog.services.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {
	@Autowired
	UserService userService;
	//Post Mapping - create user
	@PostMapping("/")
	public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto){
		UserDto createUserDto=userService.createUser(userDto);
		return new ResponseEntity<>(createUserDto,HttpStatus.CREATED);
		
	}
	//put Mapping - Update
	@PutMapping("/{userId}")
	public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto,@PathVariable("userId") Integer userId ){
		UserDto updatedUserDto=userService.updateUser(userDto, userId);
		return ResponseEntity.ok(updatedUserDto);
		
	}
	//Delete User
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{userId}")
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable("userId") Integer userId){
		userService.deleteUser(userId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("user deleted", true),HttpStatus.OK);
	}
	//Get All Users
	@GetMapping("/")
	public ResponseEntity<List<UserDto>> getAllUsers(){
		List<UserDto> users=userService.getAllUsers();
		return ResponseEntity.ok(users);
		
	}//Get user
	@GetMapping("/{userId}")
	public ResponseEntity<UserDto> getSingleUser(@PathVariable Integer userId){
		UserDto user=userService.getUserById(userId);
		return ResponseEntity.ok(user);
		
	}
	
	
	
	
}
