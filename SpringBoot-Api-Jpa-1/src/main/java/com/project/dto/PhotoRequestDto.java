package com.project.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PhotoRequestDto {

    @JsonProperty("url_small")
    private String smallUrl;

    @JsonProperty("url_medium")
    private String mediumUrl;

    @JsonProperty("url_large")
    private String largeUrl;

    @JsonProperty("description")
    private String description;

}
