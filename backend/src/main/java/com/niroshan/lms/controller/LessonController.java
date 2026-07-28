package com.niroshan.lms.controller;

import com.niroshan.lms.dto.response.LessonResponse;
import com.niroshan.lms.service.LessonService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class LessonController {

    private final LessonService lessonService;

    public LessonController(LessonService lessonService) {
        this.lessonService = lessonService;
    }

    @PostMapping(
            value = "/{courseId}/lessons",
            consumes = "multipart/form-data"
    )
    @PreAuthorize("hasRole('INSTRUCTOR')")
    public LessonResponse createLesson(
            @PathVariable Long courseId,
            @RequestParam @Valid String title,
            @RequestParam MultipartFile file
    ) throws IOException {

        if (file.isEmpty()) {
            throw new RuntimeException("PDF file required");
        }

        return lessonService.createLesson(
                courseId,
                title,
                file
        );
    }


    @GetMapping("/{courseId}/lessons")
    public List<LessonResponse> getLessons(
            @PathVariable Long courseId
    ) {

        return lessonService.getLessons(courseId);
    }


    @PutMapping(
            value = "/{courseId}/lessons/{lessonId}",
            consumes = "multipart/form-data"
    )
    @PreAuthorize("hasRole('INSTRUCTOR')")
    public LessonResponse updateLesson(
            @PathVariable Long courseId,
            @PathVariable Long lessonId,
            @RequestParam @Valid String title,
            @RequestParam MultipartFile file
    ) throws IOException {

        if (file.isEmpty()) {
            throw new RuntimeException("PDF file required");
        }

        return lessonService.updateLesson(
                courseId,
                lessonId,
                title,
                file
        );
    }


    @DeleteMapping("/{courseId}/lessons/{lessonId}")
    @PreAuthorize("hasRole('INSTRUCTOR')")
    public String deleteLesson(
            @PathVariable Long courseId,
            @PathVariable Long lessonId
    ) {

        lessonService.deleteLesson(
                courseId,
                lessonId
        );

        return "Lesson deleted successfully";
    }
}