package com.project.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.model.UserRole;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {

	@JsonProperty("User_id")
    private Integer userId;
	
	@JsonProperty("Name")
    private String name;
    
    @JsonProperty("Username")
	private String username;
	
    @JsonProperty("Role")
	private UserRole role;
	
    @JsonProperty("Email")
	private String email;
	

}
