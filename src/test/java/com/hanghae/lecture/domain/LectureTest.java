package com.hanghae.lecture.domain;

import com.hanghae.lecture.domain.exception.LectureFullException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)  // Mockito 확장을 활성화
public class LectureTest {

    @Test
    @DisplayName("신청 인원 증가 성공")
    void test1() {

        Lecture lecture = Lecture.create(2L, "Spring 특강", "이봄", LocalDate.of(2025, 1, 15), 30, 2);

        // Then
        assertNotNull(lecture);
        assertEquals(2L, lecture.getId());
        assertEquals("Spring 특강", lecture.getTitle());
        assertEquals("이봄", lecture.getInstructor());
        assertEquals(LocalDate.of(2025, 1, 15), lecture.getLectureDate());
        assertEquals(30, lecture.getCapacity());
        assertEquals(2, lecture.getCurrentEnrollment());
    }

    @Test
    @DisplayName("정원 초과 여부 확인: 미만")
    void test2() {
        // Given
        Lecture lecture = Lecture.create(1L, "Java 강의", "이봄", LocalDate.now(), 30, 25);

        // When & Then
        assertDoesNotThrow(() -> lecture.validateCapacity());
    }

    @Test
    @DisplayName("정원 초과 여부 확인: 초과")
    void test4() {
        // Given
        Lecture lecture = Lecture.create(1L, "Java 강의", "이봄", LocalDate.now(), 30, 31);

        // When & Then
        LectureFullException exception = assertThrows(
                LectureFullException.class,
                () -> lecture.validateCapacity()
        );

        assertEquals("강의 정원이 모두 찼습니다.", exception.getMessage());
    }

    @Test
    @DisplayName("신청 인원 증가 테스트")
    void test5() {
        // Given
        Lecture lecture = Lecture.create(1L, "Java 강의", "이봄", LocalDate.now(), 30, 10);

        // When
        lecture.incrementEnrollment();

        // Then
        assertEquals(11, lecture.getCurrentEnrollment());
    }
}
