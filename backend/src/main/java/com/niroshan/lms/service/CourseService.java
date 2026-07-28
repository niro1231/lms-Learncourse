package com.niroshan.lms.service;


import com.niroshan.lms.dto.request.CourseRequest;
import com.niroshan.lms.dto.request.CourseUpdateRequest;
import com.niroshan.lms.dto.response.CourseResponse;
import com.niroshan.lms.entity.Course;
import com.niroshan.lms.entity.User;
import com.niroshan.lms.repository.CourseRepository;
import com.niroshan.lms.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import com.niroshan.lms.dto.response.CourseDetailsResponse;
import com.niroshan.lms.dto.response.LessonResponse;
import com.niroshan.lms.repository.RatingRepository;
import org.springframework.web.multipart.MultipartFile;

@Service
public class CourseService {
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final RatingRepository ratingRepository;
    private final FileStorageService fileStorageService;
    public CourseService(
            CourseRepository courseRepository,
            UserRepository userRepository,
            RatingRepository ratingRepository,
            FileStorageService fileStorageService
    ){
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
        this.ratingRepository = ratingRepository;
        this.fileStorageService = fileStorageService;
    }

    // CREATE COURSE
    public CourseResponse createCourse(
            CourseRequest request
    ) throws IOException {
        String email =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getName();
        User instructor =
                userRepository.findByEmail(email)
                        .orElseThrow(
                                () -> new RuntimeException(
                                        "User not found"
                                )
                        );
        Course course = new Course();
        course.setTitle(
                request.getTitle()
        );
        course.setDescription(
                request.getDescription()
        );
        course.setCategory(
                request.getCategory()
        );
        course.setInstructor(
                instructor
        );
        if(request.getThumbnail() != null &&
                !request.getThumbnail().isEmpty()){
            String imageUrl =
                    fileStorageService.saveImage(
                            request.getThumbnail()
                    );
            course.setThumbnailUrl(
                    imageUrl
            );
        }
        Course savedCourse =
                courseRepository.save(course);
        return convertToResponse(savedCourse);
    }

    // GET ALL COURSES
    public List<CourseResponse> getAllCourses(){
        return courseRepository.findAll()
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    // GET COURSES BY ID
    public CourseResponse getCourseById(Long id){
        Course course =
                courseRepository.findById(id)
                        .orElseThrow(
                                () -> new RuntimeException(
                                        "Course not found"
                                )
                        );
        return convertToResponse(course);
    }

    // UPDATE COURSES
    public CourseResponse updateCourse(
            Long id,
            CourseUpdateRequest request
    ) throws IOException {
        Course course =
                courseRepository.findById(id)
                        .orElseThrow(
                                () -> new RuntimeException(
                                        "Course not found"
                                )
                        );
        String email =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getName();
        if (!course.getInstructor().getEmail().equals(email)) {
            throw new RuntimeException(
                    "You cannot update this course"
            );
        }
        course.setTitle(
                request.getTitle()
        );
        course.setDescription(
                request.getDescription()
        );
        course.setCategory(
                request.getCategory()
        );
        if (request.getThumbnail() != null &&
                !request.getThumbnail().isEmpty()) {
            String imageUrl =
                    fileStorageService.saveImage(
                            request.getThumbnail()
                    );
            course.setThumbnailUrl(
                    imageUrl
            );
        }
        Course updated =
                courseRepository.save(course);
        return convertToResponse(updated);
    }

    // DELETE COURSES
    public void deleteCourse(Long id){
        Course course =
                courseRepository.findById(id)
                        .orElseThrow(
                                () -> new RuntimeException(
                                        "Course not found"
                                )
                        );
        String email =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getName();
        if(!course.getInstructor()
                .getEmail()
                .equals(email)){

            throw new RuntimeException(
                    "You cannot delete this course"
            );
        }
        courseRepository.delete(course);
    }

    public CourseDetailsResponse getCourseDetails(Long id){
        Course course =
                courseRepository.findById(id)
                        .orElseThrow(
                                () -> new RuntimeException(
                                        "Course not found"
                                )
                        );
        Double average =
                ratingRepository
                        .findAverageRating(id);
        if(average == null){
            average = 0.0;
        }
        List<LessonResponse> lessons =
                course.getLessons()
                        .stream()
                        .map(
                                lesson -> new LessonResponse(
                                        lesson.getId(),
                                        lesson.getTitle(),
                                        lesson.getPdfUrl()
                                )
                        )
                        .toList();
        return new CourseDetailsResponse(
                course.getId(),
                course.getTitle(),
                course.getDescription(),
                course.getCategory(),
                course.getInstructor().getName(),
                average,
                course.getThumbnailUrl(),
                course.getInstructor().getEmail(),
                lessons
        );
    }

    // ENTITY -> RESPONSE DTO
    private CourseResponse convertToResponse(
            Course course
    ){
        Double averageRating =
                ratingRepository.findAverageRating(
                        course.getId()
                );
        if(averageRating == null){
            averageRating = 0.0;
        }
        return new CourseResponse(
                course.getId(),
                course.getTitle(),
                course.getDescription(),
                course.getCategory(),
                course.getCreatedAt(),
                course.getInstructor()
                        .getName(),
                averageRating,
                course.getThumbnailUrl()
        );
    }

    public List<CourseResponse> getCoursesOrderByRating(){
        List<Object[]> results =
                ratingRepository.findCoursesOrderByRating();
        return results.stream()
                .map(result -> {
                    Long courseId =
                            (Long) result[0];
                    Double rating =
                            (Double) result[1];
                    Course course =
                            courseRepository.findById(courseId)
                                    .orElseThrow();
                    return new CourseResponse(
                            course.getId(),
                            course.getTitle(),
                            course.getDescription(),
                            course.getCategory(),
                            course.getCreatedAt(),
                            course.getInstructor().getName(),
                            rating,
                            course.getThumbnailUrl()
                    );
                })
                .toList();
    }

    public List<CourseResponse> getMyCourses(){
        String email =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getName();
        User instructor =
                userRepository.findByEmail(email)
                        .orElseThrow(
                                () -> new RuntimeException("Instructor not found")
                        );
        return courseRepository.findByInstructor(instructor)
                .stream()
                .map(course -> {
                    Double averageRating =
                            ratingRepository
                                    .findAverageRating(course.getId());
                    if(averageRating == null){
                        averageRating = 0.0;
                    }
                    return new CourseResponse(
                            course.getId(),
                            course.getTitle(),
                            course.getDescription(),
                            course.getCategory(),
                            course.getCreatedAt(),
                            instructor.getName(),
                            averageRating,
                            course.getThumbnailUrl()
                    );
                })
                .toList();
    }
    public List<CourseResponse> getCoursesByInstructor(Long instructorId){

        List<Course> courses =
                courseRepository.findByInstructorId(instructorId);

        return courses.stream()
                .map(this::convertToResponse)
                .toList();
    }
}