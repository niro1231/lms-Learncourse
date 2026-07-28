package com.niroshan.lms.controller;

import com.niroshan.lms.dto.request.LoginRequest;
import com.niroshan.lms.dto.request.RegisterRequest;
import com.niroshan.lms.dto.response.LoginResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.niroshan.lms.entity.User;
import com.niroshan.lms.service.UserService;
import com.niroshan.lms.dto.response.UserResponse;
import jakarta.validation.Valid;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/register", consumes = "multipart/form-data")
    public UserResponse register(
            @Valid @ModelAttribute RegisterRequest request
    ) throws IOException {
        return userService.registerUser(request);
    }

    @PostMapping("/login")
    public LoginResponse login(
            @Valid @RequestBody LoginRequest request
    ){
        return userService.loginUser(request);
    }

    @GetMapping("/profile")
    public String profile(){
        return "My Profile";
    }
    @GetMapping("/student")
    public String student(){
        return "Welcome Student";
    }
    @GetMapping("/instructor")
    public String instructor(){
        return "Welcome Instructor";
    }
    @GetMapping("/admin")
    public String admin(){
        return "Welcome Admin";
    }
}