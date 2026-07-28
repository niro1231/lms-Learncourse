package com.niroshan.lms.controller;


import com.niroshan.lms.service.AdminService;
import com.niroshan.lms.dto.response.UserResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
    private final AdminService adminService;
    public AdminController(
            AdminService adminService
    ){
        this.adminService = adminService;
    }
    @GetMapping("/users")
    public List<UserResponse> getAllUsers(){
        return adminService.getAllUsers();
    }
    @DeleteMapping("/users/{id}")
    public String deleteUser(
            @PathVariable Long id
    ){
        adminService.deleteUser(id);
        return "User deleted successfully";
    }
    @DeleteMapping("/courses/{id}")
    public String deleteCourse(
            @PathVariable Long id
    ){
        adminService.deleteCourse(id);
        return "Course deleted successfully";
    }
}