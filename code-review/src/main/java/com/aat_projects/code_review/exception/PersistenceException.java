package com.aat_projects.code_review.exception;

public class PersistenceException extends RuntimeException{
    public PersistenceException(String message, Throwable cause){
        super(message,cause);
    }
}
