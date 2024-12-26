package com.hanghae.lecture.application;

import com.hanghae.lecture.application.exception.LectureNotFoundException;
import com.hanghae.lecture.domain.Lecture;
import com.hanghae.lecture.domain.LectureRegistration;
import com.hanghae.lecture.domain.LectureRegistrationRepository;
import com.hanghae.lecture.domain.LectureRepository;
import com.hanghae.lecture.domain.exception.DuplicateRegistrationException;
import com.hanghae.lecture.domain.exception.LectureFullException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)  // Mockito 확장을 활성화
public class LectureRegistrationServiceTest {

    private static final Logger log = LoggerFactory.getLogger(LectureRegistrationServiceTest.class);

    @Mock
    private LectureRegistrationRepository lectureRegistrationRepository; // Mock 객체 생성

    @Mock
    private LectureRepository lectureRepository; // Mock 객체 생성

    @InjectMocks
    private LectureRegistrationServiceImpl lectureRegistrationService; // 의존성 주입 자동 처리

    @DisplayName("강의 정보 조회 - 실패")
    @Test
    void test1(){
        // given
        given(lectureRepository.findByIdWithLock(1L))
                .willReturn(Optional.empty());

        LectureRegistration registration = new LectureRegistration(null, 1L, 1L, LocalDateTime.now());        // When & Then
        LectureNotFoundException exception = assertThrows(LectureNotFoundException.class,
                () -> lectureRegistrationService.applyLecture(registration));

        assertEquals("해당 강의가 존재하지 않습니다.", exception.getMessage());
        log.info("Exception message: = [{}]", exception.getMessage());
    }

    @DisplayName("강의 신청 - 성공")
    @Test
    void test2() {
        // given
        Lecture lecture = mock(Lecture.class);
        LectureRegistration registration = new LectureRegistration(null, 1L, 1L, LocalDateTime.now());

        // Mock: 중복 신청 여부
        given(lectureRegistrationRepository.existsByUserIdAndLectureId(1L, 1L)).willReturn(false);

        // Mock: 강의 정보 조회
        given(lectureRepository.findByIdWithLock(1L)).willReturn(Optional.of(lecture));

        // when
        lectureRegistrationService.applyLecture(registration);

        // then
        verify(lectureRegistrationRepository, times(1)).save(registration); // 등록 데이터 저장
        verify(lecture, times(1)).incrementEnrollment(); // 신청 인원 증가
        verify(lectureRepository, times(1)).save(lecture); // 강의 데이터 저장
    }

    @DisplayName("신청 완료 목록 반환")
    @Test
    void test3() {
        // Given
        Long userId = 1L;
        List<Lecture> mockLectures = List.of(
                new Lecture(1L, "Spring Boot 강의", "김봄", LocalDate.of(2024, 12, 20),100, 50),
                new Lecture(2L, "Java 기초", "김자바", LocalDate.of(2024, 12, 23),30, 30)
        );

        // Mock: 신청 완료 강의 조회
        given(lectureRegistrationRepository.findCompletedleLectures(userId)).willReturn(mockLectures);

        // When
        List<Lecture> lectures = lectureRegistrationService.getCompletedLectures(userId);

        // Then
        assertEquals(2, lectures.size());
        assertEquals("Spring Boot 강의", lectures.get(0).getTitle());
        assertEquals("Java 기초", lectures.get(1).getTitle());
        verify(lectureRegistrationRepository, times(1)).findCompletedleLectures(userId);
    }


}
