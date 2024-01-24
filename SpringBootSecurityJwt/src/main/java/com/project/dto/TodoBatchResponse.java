package com.project.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TodoBatchResponse {

	private Long id;

    private String name;

    private String description;

    private String status;

    private Integer totalPage;

    private long totalRecord;

    private Integer currentPage;

    private Integer pageSize;
}
