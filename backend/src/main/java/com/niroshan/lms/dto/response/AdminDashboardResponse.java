package com.niroshan.lms.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AdminDashboardResponse {
    private long totalUsers;
    private long totalStudents;
    private long totalInstructors;
    private long totalCourses;
}