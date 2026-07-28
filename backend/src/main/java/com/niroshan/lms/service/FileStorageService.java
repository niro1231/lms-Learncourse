package com.niroshan.lms.service;

import com.niroshan.lms.exception.FileUploadException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.UUID;

@Service
public class FileStorageService {
    private final Path uploadPath =
            Paths.get("uploads");
    // =========================
    // SAVE PDF LESSON FILE
    // =========================
    public String savePdf(MultipartFile file)
            throws IOException {
        validateFile(file);
        String contentType =
                file.getContentType();
        if(contentType == null ||
                !contentType.equals("application/pdf")){
            throw new FileUploadException(
                    "Only PDF files are allowed"
            );
        }
        return save(file);
    }
    // =========================
    // SAVE COURSE IMAGE
    // =========================

    public String saveImage(MultipartFile file)
            throws IOException {
        validateFile(file);
        String contentType =
                file.getContentType();
        if(contentType == null ||
                (!contentType.equals("image/jpeg")
                        &&
                        !contentType.equals("image/png"))){
            throw new FileUploadException(
                    "Only JPG or PNG images are allowed"
            );
        }
        return save(file);
    }
    // =========================
    // COMMON VALIDATION
    // =========================

    private void validateFile(
            MultipartFile file
    ){
        if(file.isEmpty()){

            throw new FileUploadException(
                    "File is empty"
            );
        }
        long maxSize =
                10 * 1024 * 1024;
        if(file.getSize() > maxSize){
            throw new FileUploadException(
                    "File size must be less than 10MB"
            );
        }
    }
    // =========================
    // ACTUAL SAVE METHOD
    // =========================

    private String save(
            MultipartFile file
    )
            throws IOException {
        if(!Files.exists(uploadPath)){
            Files.createDirectories(
                    uploadPath
            );
        }
        String originalFilename =
                file.getOriginalFilename();
        if(originalFilename == null){
            throw new FileUploadException(
                    "Invalid filename"
            );
        }
        originalFilename =
                Paths.get(originalFilename)
                        .getFileName()
                        .toString();
        String filename =
                UUID.randomUUID()
                        + "_"
                        + originalFilename;
        Path filePath =
                uploadPath.resolve(filename);
        Files.copy(
                file.getInputStream(),
                filePath,
                StandardCopyOption.REPLACE_EXISTING
        );
        return "files/" + filename;
    }
}