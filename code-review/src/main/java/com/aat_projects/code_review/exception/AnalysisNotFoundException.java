package com.aat_projects.code_review.exception;

public class AnalysisNotFoundException extends RuntimeException{
    public AnalysisNotFoundException(Long id){
        super("Analysis not found for id: " + id);
    }

    public AnalysisNotFoundException(String message){
        super(message);
    }
}
