package com.niroshan.lms.controller;

import com.niroshan.lms.dto.response.AdminDashboardResponse;
import com.niroshan.lms.service.AdminDashboardService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class AdminDashboardController {
    private final AdminDashboardService dashboardService;
    public AdminDashboardController(
            AdminDashboardService dashboardService
    ){
        this.dashboardService = dashboardService;
    }
    @GetMapping("/dashboard")
    @PreAuthorize("hasRole('ADMIN')")
    public AdminDashboardResponse dashboard(){
        return dashboardService.getDashboard();
    }
}