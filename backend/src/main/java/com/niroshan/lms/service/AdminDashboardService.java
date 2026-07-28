package com.niroshan.lms.service;


import com.niroshan.lms.dto.response.AdminDashboardResponse;
import com.niroshan.lms.entity.Role;
import com.niroshan.lms.repository.CourseRepository;
import com.niroshan.lms.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class AdminDashboardService {
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    public AdminDashboardService(
            UserRepository userRepository,
            CourseRepository courseRepository
    ){
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
    }
    public AdminDashboardResponse getDashboard(){
        long totalUsers =
                userRepository.count();
        long students =
                userRepository.countByRole(
                        Role.STUDENT
                );
        long instructors =
                userRepository.countByRole(
                        Role.INSTRUCTOR
                );
        long courses =
                courseRepository.count();
        return new AdminDashboardResponse(
                totalUsers,
                students,
                instructors,
                courses
        );
    }
}