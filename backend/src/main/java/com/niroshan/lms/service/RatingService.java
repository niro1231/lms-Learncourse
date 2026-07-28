package com.niroshan.lms.service;


import com.niroshan.lms.dto.request.RatingRequest;
import com.niroshan.lms.dto.response.RatingResponse;
import com.niroshan.lms.entity.*;
import com.niroshan.lms.exception.ResourceNotFoundException;
import com.niroshan.lms.repository.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RatingService {
    private final RatingRepository ratingRepository;
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    public RatingService(
            RatingRepository ratingRepository,
            CourseRepository courseRepository,
            UserRepository userRepository
    ){
        this.ratingRepository = ratingRepository;
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
    }
    public RatingResponse addRating(
            Long courseId,
            RatingRequest request
    ){
        String email =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getName();
        User user =
                userRepository.findByEmail(email)
                        .orElseThrow(
                                () -> new ResourceNotFoundException(
                                        "User not found"
                                )
                        );
        Course course =
                courseRepository.findById(courseId)
                        .orElseThrow(
                                () -> new ResourceNotFoundException(
                                        "Course not found"
                                )
                        );
        Rating rating = new Rating();
        rating.setRating(request.getRating());
        rating.setComment(request.getComment());
        rating.setUser(user);
        rating.setCourse(course);
        Rating saved =
                ratingRepository.save(rating);
        return new RatingResponse(
                user.getName(),
                saved.getRating(),
                saved.getComment()
        );
    }
    public List<RatingResponse> getRatings(
            Long courseId
    ){
        return ratingRepository.findByCourseId(courseId)
                .stream()
                .map(
                        rating ->
                                new RatingResponse(
                                        rating.getUser().getName(),
                                        rating.getRating(),
                                        rating.getComment()
                                )
                )
                .toList();
    }
}