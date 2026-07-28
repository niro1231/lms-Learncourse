package com.niroshan.lms.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RatingResponse {
    private String studentName;
    private Integer rating;
    private String comment;
}