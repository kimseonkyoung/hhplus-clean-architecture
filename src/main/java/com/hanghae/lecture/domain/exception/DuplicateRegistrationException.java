package com.hanghae.lecture.domain.exception;

public class DuplicateRegistrationException extends RuntimeException{
    public DuplicateRegistrationException(String message){
        super(message);
    }
}
