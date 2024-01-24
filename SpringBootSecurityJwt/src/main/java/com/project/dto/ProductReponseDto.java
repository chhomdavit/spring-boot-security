package com.project.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProductReponseDto {

	private  List<ProductDto> productDtos;
    private  Integer pageNumber;
    private  Integer pageSize;
    private  Long totalElements;
    private  int totalPages;
    private  boolean isLast;
}
