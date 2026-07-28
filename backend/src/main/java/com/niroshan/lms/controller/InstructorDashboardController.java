package com.niroshan.lms.controller;


import com.niroshan.lms.dto.response.InstructorDashboardResponse;
import com.niroshan.lms.service.InstructorDashboardService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/instructor")
public class InstructorDashboardController {
    private final InstructorDashboardService dashboardService;
    public InstructorDashboardController(
            InstructorDashboardService dashboardService
    ){
        this.dashboardService = dashboardService;
    }
    @GetMapping("/dashboard")
    @PreAuthorize("hasRole('INSTRUCTOR')")
    public InstructorDashboardResponse dashboard()
    {
        return dashboardService.getDashboard();
    }
    @GetMapping("/{id}")
    public InstructorDashboardResponse getInstructor(
            @PathVariable Long id
    ) {
        return dashboardService.getInstructorById(id);
    }
}