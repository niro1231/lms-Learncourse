package com.niroshan.lms.repository;

import com.niroshan.lms.entity.CourseLesson;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CourseLessonRepository extends JpaRepository<CourseLesson,Long> {
    List<CourseLesson> findByCourseId(Long courseId);
    long countByCourseInstructorId(Long instructorId);
}
