package com.niroshan.lms.controller;

import com.niroshan.lms.dto.request.RatingRequest;
import com.niroshan.lms.dto.response.RatingResponse;
import com.niroshan.lms.service.RatingService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class RatingController {
    private final RatingService ratingService;
    public RatingController(
            RatingService ratingService
    ){
        this.ratingService = ratingService;
    }
    @PostMapping("/{courseId}/ratings")
    public RatingResponse addRating(
            @PathVariable Long courseId,
            @Valid @RequestBody RatingRequest request
    ){
        return ratingService.addRating(
                courseId,
                request
        );
    }
    @GetMapping("/{courseId}/ratings")
    public List<RatingResponse> getRatings(
            @PathVariable Long courseId
    ){
        return ratingService.getRatings(courseId);
    }
}