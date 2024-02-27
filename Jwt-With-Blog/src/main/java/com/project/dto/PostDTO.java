package com.project.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PostDTO {

	private Integer postId;
	
	private String title;
	
	private String content;
	
	private String postImage;
	
	private String postImgUrl;
	
    private LocalDateTime created;
	
    private LocalDateTime updated;
	
	private UserDTO user;
}
