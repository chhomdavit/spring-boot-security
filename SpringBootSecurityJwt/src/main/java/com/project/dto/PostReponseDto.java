package com.project.dto;


import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostReponseDto {

    private  List<PostDto> postRequestDtos;
    private  Integer pageNumber;
    private  Integer pageSize;
    private  Long totalElements;
    private  int totalPages;
    private  boolean isLast;
   
}
