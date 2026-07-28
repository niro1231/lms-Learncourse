package com.niroshan.lms.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LessonRequest {
    @NotBlank(message = "Lesson title required")
    private String title;
}