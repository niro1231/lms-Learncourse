package com.niroshan.lms.dto.response;

import lombok.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class CourseDetailsResponse {
    private Long id;
    private String title;
    private String description;
    private String category;
    private String instructorName;
    private Double averageRating;
    private String thumbnailUrl;
    private String instructorEmail;
    private List<LessonResponse> lessons;
}