package com.hanghae.lecture.application.exception;

public class LectureNotFoundException extends RuntimeException{
    public LectureNotFoundException(String message){
        super(message);
    }
}
