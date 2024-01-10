package com.codewithdurgesh.blog.payloads;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.codewithdurgesh.blog.entities.Role;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@NoArgsConstructor
@Getter
@Setter

public class UserDto {
	private int id;
	@NotEmpty
	@Size(min= 4, message ="Username must be more than 4 char")
	private String name;
	@Email(message="email adress is not valid")
	private String email;
	
	@Size(min=4, max=10,message="password must b emin 4char length and 10 char max")
	private String password;
	@NotEmpty
	private String about;
	private Set<RoleDto> roles=new HashSet<>();
}
