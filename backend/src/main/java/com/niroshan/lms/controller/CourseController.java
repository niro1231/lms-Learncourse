package com.niroshan.lms.controller;

import com.niroshan.lms.dto.request.CourseRequest;
import com.niroshan.lms.dto.request.CourseUpdateRequest;
import com.niroshan.lms.dto.response.CourseDetailsResponse;
import com.niroshan.lms.dto.response.CourseResponse;
import com.niroshan.lms.service.CourseService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class CourseController {
    private final CourseService courseService;
    public CourseController(
            CourseService courseService
    ) {
        this.courseService = courseService;
    }

    @PostMapping(consumes = "multipart/form-data")
    @PreAuthorize("hasRole('INSTRUCTOR')")
    public CourseResponse createCourse(
            @Valid @ModelAttribute CourseRequest request
    ) throws IOException {
        return courseService.createCourse(request);
    }

    @GetMapping
    public List<CourseResponse> getAllCourses() {
        return courseService.getAllCourses();
    }

    @GetMapping("/{id}")
    public CourseDetailsResponse getCourseById(
            @PathVariable Long id
    ){
        return courseService.getCourseDetails(id);
    }

    @PutMapping( value = "/{id}", consumes = "multipart/form-data")
    @PreAuthorize("hasRole('INSTRUCTOR')")
    public CourseResponse updateCourse(
            @PathVariable Long id,
            @Valid @ModelAttribute CourseUpdateRequest request
    ) throws IOException {
        return courseService.updateCourse(id, request);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('INSTRUCTOR')")
    public String deleteCourse(
            @PathVariable Long id
    ){
        courseService.deleteCourse(id);
        return "Course deleted successfully";
    }

    @GetMapping("/sort/rating")
    public List<CourseResponse> getCoursesByRating(){
        return courseService.getCoursesOrderByRating();
    }

    @GetMapping("/my-courses")
    @PreAuthorize("hasRole('INSTRUCTOR')")
    public List<CourseResponse> getMyCourses(){
        return courseService.getMyCourses();
    }
    @GetMapping("/instructor/{instructorId}")
    public List<CourseResponse> getCoursesByInstructor(
            @PathVariable Long instructorId
    ){
        return courseService.getCoursesByInstructor(instructorId);
    }
}