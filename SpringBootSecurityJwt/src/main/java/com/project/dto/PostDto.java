package com.project.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {

    private String title;
	
	private String description;
	
	private String img_post;  
	
	private String img_postUrl;
	
    private LocalDateTime created;
	
    private LocalDateTime updated;
}
