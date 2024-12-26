package com.hanghae.lecture.domain.exception;

public class LectureFullException extends RuntimeException{
    public LectureFullException(String message){
        super(message);
    }
}
