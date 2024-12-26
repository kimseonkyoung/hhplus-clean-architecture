package com.hanghae.lecture.domain;

import com.hanghae.lecture.domain.exception.LectureFullException;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "lectures")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Lecture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lecture_id")
    private Long id;

    @Column(name = "title", nullable = false, length = 100)
    private String title;

    @Column(name = "instructor", nullable = false, length = 20)
    private String instructor;

    @Column(name = "lecture_date")
    private LocalDate lectureDate;

    @Column(name = "capacity", nullable = false)
    private int capacity;

    @Column(name = "current_enrollment", nullable = false)
    private int currentEnrollment;

    // 생성자를 팩토리 메서드로 생성
    public static Lecture create(long lectureId, String title, String instructor, LocalDate lectureDate, int capacity, int currentEnrollment) {
        return new Lecture(lectureId, title, instructor, lectureDate, capacity, currentEnrollment);
    }

    // 신청 인원 증가
    public void incrementEnrollment() {
        this.currentEnrollment++;
    }

    // 정원 초과 여부 확인
    public void validateCapacity(){
        if (currentEnrollment >= capacity) {
            throw new LectureFullException("강의 정원이 모두 찼습니다.");
        }
    }
}
