package com.niroshan.lms.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class InstructorDashboardResponse {
    private String instructorName;
    private long totalCourses;
    private long totalLessons;
    private double averageRating;
    private String profileImageUrl;
}