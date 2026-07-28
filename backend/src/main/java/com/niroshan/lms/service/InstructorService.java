package com.niroshan.lms.service;


import com.niroshan.lms.dto.response.InstructorResponse;
import com.niroshan.lms.entity.Role;
import com.niroshan.lms.entity.User;
import com.niroshan.lms.repository.CourseRepository;
import com.niroshan.lms.repository.UserRepository;
import com.niroshan.lms.repository.RatingRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class InstructorService {
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final RatingRepository ratingRepository;
    public InstructorService(
            UserRepository userRepository,
            CourseRepository courseRepository,
            RatingRepository ratingRepository
    ) {
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
        this.ratingRepository = ratingRepository;
    }
    public List<InstructorResponse> getAllInstructors() {
        List<User> instructors =
                userRepository.findByRole(Role.INSTRUCTOR);
        return instructors.stream()
                .map(user -> {
                    Long courseCount =
                            courseRepository
                                    .countByInstructorId(
                                            user.getId()
                                    );
                    Double averageRating =
                            ratingRepository
                                    .findAverageRatingByInstructor(
                                            user.getId()
                                    );
                    averageRating = formatRating(averageRating);
                    return new InstructorResponse(
                            user.getId(),
                            user.getName(),
                            user.getEmail(),
                            courseCount,
                            averageRating,
                            user.getProfileImageUrl()
                    );
                })
                .toList();
    }

    private double formatRating(Double rating) {
        if (rating == null) {
            return 0.0;
        }
        return Math.round(rating * 100.0) / 100.0;
    }
}