package com.niroshan.lms.repository;

import com.niroshan.lms.entity.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface RatingRepository extends JpaRepository<Rating,Long> {
    List<Rating> findByCourseId(Long courseId);
    @Query(
            "SELECT AVG(r.rating) FROM Rating r WHERE r.course.id = :courseId"
    )
    Double findAverageRating(Long courseId);

    @Query(
            "SELECT AVG(r.rating) " +
                    "FROM Rating r " +
                    "WHERE r.course.instructor.id = :instructorId"
    )
    Double findAverageRatingByInstructor(
            Long instructorId
    );
    @Query("""
            SELECT r.course.id, AVG(r.rating)
            FROM Rating r
            GROUP BY r.course.id
            ORDER BY AVG(r.rating) DESC
            """)
    List<Object[]> findCoursesOrderByRating();
}