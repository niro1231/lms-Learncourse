package com.niroshan.lms.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class InstructorResponse {
    private Long id;
    private String name;
    private String email;
    private Long courseCount;
    private Double averageRating;
    private String profileImageUrl;
}