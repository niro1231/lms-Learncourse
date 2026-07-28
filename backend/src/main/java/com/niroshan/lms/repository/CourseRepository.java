package com.niroshan.lms.repository;

import com.niroshan.lms.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import com.niroshan.lms.entity.Course;
import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {
    long countByInstructorId(Long instructorId);
    List<Course> findByInstructor(User instructor);
    List<Course> findByInstructorId(Long instructorId);
}