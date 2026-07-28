package com.niroshan.lms.service;


import com.niroshan.lms.dto.response.UserResponse;
import com.niroshan.lms.entity.User;
import com.niroshan.lms.repository.CourseRepository;
import com.niroshan.lms.repository.UserRepository;
import org.springframework.stereotype.Service;
import com.niroshan.lms.entity.Course;
import java.util.List;

@Service
public class AdminService {
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    public AdminService(
            UserRepository userRepository,
            CourseRepository courseRepository
    ){
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
    }
    public List<UserResponse> getAllUsers(){
        return userRepository.findAll()
                .stream()
                .map(
                        user -> new UserResponse(
                                user.getId(),
                                user.getName(),
                                user.getEmail(),
                                user.getRole().name(),
                                user.getProfileImageUrl()
                        )
                )
                .toList();
    }

    public void deleteUser(Long id){
        User user =
                userRepository.findById(id)
                        .orElseThrow(
                                () -> new RuntimeException(
                                        "User not found"
                                )
                        );
        userRepository.delete(user);
    }

    public void deleteCourse(Long id){
        Course course =
                courseRepository.findById(id)
                        .orElseThrow(
                                () -> new RuntimeException(
                                        "Course not found"
                                )
                        );
        courseRepository.delete(course);
    }
}