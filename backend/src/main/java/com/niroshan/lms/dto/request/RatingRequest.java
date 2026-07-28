package com.niroshan.lms.dto.request;


import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RatingRequest {
    @Min(1)
    @Max(5)
    private Integer rating;
    private String comment;
}