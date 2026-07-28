package com.niroshan.lms.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "courses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String title;
    @Column(length = 2000)
    private String description;
    private String category;
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "instructor_id")
    private User instructor;
    @PrePersist
    public void prePersist(){
        createdAt = LocalDateTime.now();
    }
    @OneToMany(
            mappedBy = "course",
            cascade = CascadeType.ALL
    )
    private List<CourseLesson> lessons;
    @OneToMany(
            mappedBy = "course",
            cascade = CascadeType.ALL
    )
    private List<Rating> ratings;
    private String thumbnailUrl;
}