package com.aat_projects.code_review.exception;

import com.aat_projects.code_review.api.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AnalysisNotFoundException.class)
    public static ResponseEntity<ErrorResponse> handleAnalysisNotFound(AnalysisNotFoundException exception){
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(
                        ErrorResponse.builder()
                                .code("ANALYSIS_NOT_FOUND")
                                .message(exception.getMessage())
                                .build()
                );
    }

    @ExceptionHandler(PersistenceException.class)
    public static ResponseEntity<ErrorResponse> handlePersistenceError(PersistenceException exception){
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(
                        ErrorResponse.builder()
                                .code("PERSISTENCE_ERROR")
                                .message(exception.getMessage())
                                .build()
                );
    }

    @ExceptionHandler(Exception.class)
    public static ResponseEntity<ErrorResponse> handleGeneric(Exception exception){
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(
                        ErrorResponse.builder()
                                .code("INTERNAL_ERROR")
                                .message("Unexpected error occurred")
                                .build()
                );
    }
}
