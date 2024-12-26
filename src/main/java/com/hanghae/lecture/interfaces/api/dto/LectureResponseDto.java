package com.hanghae.lecture.interfaces.api.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class LectureResponseDto {
    private Long lectureId;
    private String title;
    private String instructor;
    private LocalDate lectureDate;
    private int capacity;
    private int currentEnrollment;

    public LectureResponseDto(Long lectureId, String title, String instructor, LocalDate lectureDate) {
        this.lectureId = lectureId;
        this.title = title;
        this.instructor = instructor;
        this.lectureDate = lectureDate;
    }
}
