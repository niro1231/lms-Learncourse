package com.niroshan.lms.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LessonResponse {
    private Long id;
    private String title;
    private String pdfUrl;
}