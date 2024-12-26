package com.hanghae.lecture.interfaces.exception;

import com.hanghae.lecture.application.exception.LectureNotFoundException;
import com.hanghae.lecture.domain.exception.DuplicateRegistrationException;
import com.hanghae.lecture.domain.exception.LectureFullException;
import com.hanghae.lecture.interfaces.api.dto.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler  {
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        return ResponseEntity.status(500).body(new ErrorResponse("500", "에러가 발생했습니다."));
    }

    @ExceptionHandler(value = DuplicateRegistrationException.class)
    public ResponseEntity<ErrorResponse> DuplicateException(Exception e) {
        return ResponseEntity.status(400).body(new ErrorResponse("400", e.getMessage()));
    }

    @ExceptionHandler(value = LectureNotFoundException.class)
    public ResponseEntity<ErrorResponse> LectureNotFoundException(Exception e) {
        return ResponseEntity.status(400).body(new ErrorResponse("404", e.getMessage()));
    }

    @ExceptionHandler(value = LectureFullException.class)
    public ResponseEntity<ErrorResponse> LectureFullException(Exception e) {
        return ResponseEntity.status(400).body(new ErrorResponse("409", e.getMessage()));
    }
}
