package com.niroshan.lms.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.time.LocalDateTime;
@Getter
@AllArgsConstructor
public class CourseResponse {
    private Long id;
    private String title;
    private String description;
    private String category;
    private LocalDateTime createdAt;
    private String instructorName;
    private Double averageRating;
    private String thumbnailUrl;
}