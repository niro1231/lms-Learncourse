package com.niroshan.lms.service;

import com.niroshan.lms.dto.request.LessonRequest;
import com.niroshan.lms.dto.response.LessonResponse;
import com.niroshan.lms.entity.Course;
import com.niroshan.lms.entity.CourseLesson;
import com.niroshan.lms.entity.User;
import com.niroshan.lms.repository.CourseLessonRepository;
import com.niroshan.lms.repository.CourseRepository;
import com.niroshan.lms.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@Service
public class LessonService {

    private final CourseRepository courseRepository;
    private final CourseLessonRepository lessonRepository;
    private final UserRepository userRepository;
    private final FileStorageService fileStorageService;

    public LessonService(
            CourseRepository courseRepository,
            CourseLessonRepository lessonRepository,
            UserRepository userRepository,
            FileStorageService fileStorageService
    ) {
        this.courseRepository = courseRepository;
        this.lessonRepository = lessonRepository;
        this.userRepository = userRepository;
        this.fileStorageService = fileStorageService;
    }

    // Create Lesson
    public LessonResponse createLesson(
            Long courseId,
            String title,
            MultipartFile file
    ) throws IOException {

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() ->
                        new RuntimeException("Course not found"));

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User instructor = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        if (!course.getInstructor().getId().equals(instructor.getId())) {
            throw new RuntimeException(
                    "You can only add lessons to your own courses."
            );
        }

        String pdfUrl = fileStorageService.savePdf(file);

        CourseLesson lesson = new CourseLesson();
        lesson.setTitle(title);
        lesson.setPdfUrl(pdfUrl);
        lesson.setCourse(course);

        CourseLesson saved = lessonRepository.save(lesson);

        return new LessonResponse(
                saved.getId(),
                saved.getTitle(),
                saved.getPdfUrl()
        );
    }

    // Get all lessons for a course
    public List<LessonResponse> getLessons(Long courseId) {
        return lessonRepository.findByCourseId(courseId)
                .stream()
                .map(lesson -> new LessonResponse(
                        lesson.getId(),
                        lesson.getTitle(),
                        lesson.getPdfUrl()
                ))
                .toList();
    }

    public LessonResponse updateLesson(
            Long courseId,
            Long lessonId,
            String title,
            MultipartFile file
    ) throws IOException {

        Course course =
                courseRepository.findById(courseId)
                        .orElseThrow(
                                () -> new RuntimeException("Course not found")
                        );

        checkOwnership(course);

        CourseLesson lesson =
                lessonRepository.findById(lessonId)
                        .orElseThrow(
                                () -> new RuntimeException("Lesson not found")
                        );

        lesson.setTitle(title);

        if (file != null && !file.isEmpty()) {
            String pdfUrl =
                    fileStorageService.savePdf(file);

            lesson.setPdfUrl(pdfUrl);
        }

        CourseLesson updated =
                lessonRepository.save(lesson);

        return new LessonResponse(
                updated.getId(),
                updated.getTitle(),
                updated.getPdfUrl()
        );
    }

    public void deleteLesson(
            Long courseId,
            Long lessonId
    ){
        Course course =
                courseRepository.findById(courseId)
                        .orElseThrow(
                                () -> new RuntimeException("Course not found")
                        );
        checkOwnership(course);
        CourseLesson lesson =
                lessonRepository.findById(lessonId)
                        .orElseThrow(
                                () -> new RuntimeException("Lesson not found")
                        );

        lessonRepository.delete(lesson);

    }

    private void checkOwnership(Course course){
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
        if(!course.getInstructor()
                .getId()
                .equals(instructor.getId())){
            throw new RuntimeException(
                    "You don't own this course"
            );
        }
    }
}