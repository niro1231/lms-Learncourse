package com.niroshan.lms.service;


import com.niroshan.lms.dto.response.InstructorDashboardResponse;
import com.niroshan.lms.entity.User;
import com.niroshan.lms.repository.CourseLessonRepository;
import com.niroshan.lms.repository.CourseRepository;
import com.niroshan.lms.repository.UserRepository;
import com.niroshan.lms.repository.RatingRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


@Service
public class InstructorDashboardService {
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final CourseLessonRepository lessonRepository;
    private final RatingRepository ratingRepository;
    public InstructorDashboardService(
            UserRepository userRepository,
            CourseRepository courseRepository,
            CourseLessonRepository lessonRepository,
            RatingRepository ratingRepository
    ) {
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
        this.lessonRepository = lessonRepository;
        this.ratingRepository = ratingRepository;
    }

    public InstructorDashboardResponse getDashboard() {
        String email =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getName();
        User instructor =
                userRepository.findByEmail(email)
                        .orElseThrow(
                                () -> new RuntimeException("User not found")
                        );
        long totalCourses =
                courseRepository
                        .countByInstructorId(
                                instructor.getId()
                        );
        long totalLessons =
                lessonRepository
                        .countByCourseInstructorId(
                                instructor.getId()
                        );
        Double averageRating =
                ratingRepository
                        .findAverageRatingByInstructor(
                                instructor.getId()
                        );
        averageRating = formatRating(averageRating);
        return new InstructorDashboardResponse(
                instructor.getName(),
                totalCourses,
                totalLessons,
                averageRating,
                instructor.getProfileImageUrl()
        );
    }
    public InstructorDashboardResponse getInstructorById(Long id) {
        User instructor =
                userRepository.findById(id)
                        .orElseThrow(
                                () -> new RuntimeException("Instructor not found")
                        );
        long totalCourses =
                courseRepository
                        .countByInstructorId(
                                instructor.getId()
                        );
        long totalLessons =
                lessonRepository
                        .countByCourseInstructorId(
                                instructor.getId()
                        );
        Double averageRating =
                ratingRepository
                        .findAverageRatingByInstructor(
                                instructor.getId()
                        );
        averageRating = formatRating(averageRating);
        return new InstructorDashboardResponse(
                instructor.getName(),
                totalCourses,
                totalLessons,
                averageRating,
                instructor.getProfileImageUrl()
        );
    }
    private double formatRating(Double rating) {
        if (rating == null) {
            return 0.0;
        }
        return Math.round(rating * 100.0) / 100.0;
    }
}