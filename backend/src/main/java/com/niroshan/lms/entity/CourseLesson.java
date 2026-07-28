package com.niroshan.lms.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name="course_lessons")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CourseLesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String pdfUrl;
    private LocalDateTime createdAt;
    @ManyToOne
    @JoinColumn(name="course_id")
    private Course course;
    @PrePersist
    public void prePersist(){
        createdAt = LocalDateTime.now();
    }
}