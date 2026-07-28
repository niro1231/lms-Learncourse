package com.niroshan.lms.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import com.niroshan.lms.exception.FileUploadException;


@RestControllerAdvice
public class GlobalExceptionHandler {

    // Resource Not Found
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(
            ResourceNotFoundException ex
    ) {
        ErrorResponse error =
                new ErrorResponse(
                        404,
                        ex.getMessage()
                );
        return new ResponseEntity<>(
                error,
                HttpStatus.NOT_FOUND
        );
    }

    // DTO Validation Errors
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationErrors(
            MethodArgumentNotValidException ex
    ) {
        String message =
                ex.getBindingResult()
                        .getFieldErrors()
                        .get(0)
                        .getDefaultMessage();
        ErrorResponse error =
                new ErrorResponse(
                        400,
                        message
                );
        return new ResponseEntity<>(
                error,
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(FileUploadException.class)
    public ResponseEntity<ErrorResponse> handleFileUpload(
            FileUploadException ex
    ){
        ErrorResponse error =
                new ErrorResponse(
                        400,
                        ex.getMessage()
                );
        return new ResponseEntity<>(
                error,
                HttpStatus.BAD_REQUEST
        );
    }

    // General Runtime Errors
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntimeException(
            RuntimeException ex
    ) {
        ErrorResponse error =
                new ErrorResponse(
                        400,
                        ex.getMessage()
                );
        return new ResponseEntity<>(
                error,
                HttpStatus.BAD_REQUEST
        );
    }
}