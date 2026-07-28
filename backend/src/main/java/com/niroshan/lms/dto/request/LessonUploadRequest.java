package com.niroshan.lms.dto.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class LessonUploadRequest {
    private String title;
    private MultipartFile file;


}