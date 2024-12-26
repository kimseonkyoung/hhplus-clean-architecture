package com.hanghae.lecture.application;

import com.hanghae.lecture.domain.Lecture;
import com.hanghae.lecture.domain.LectureRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class) // Mockito 확장을 활성화
class LectureServiceTest {

    private static final Logger log = LoggerFactory.getLogger(LectureServiceTest.class);

    @Mock
    private LectureRepository lectureRepository; // Mock 객체 생성

    @InjectMocks
    private LectureServiceImpl lectureService; // 의존성 주입 자동 처리

    @DisplayName("특정 사용자가 신청 가능한 강의 목록 반환 테스트")
    @Test
     void test1() {
        // given
        List<Lecture> mockLectures = Arrays.asList(
                new Lecture(1L, "Java 특강", "홍자바", LocalDate.of(2024, 12, 31), 30, 1),
                new Lecture(2L, "Spring 특강", "이봄", LocalDate.of(2025, 1, 15), 30, 2)
        );

        given(lectureRepository.findAvailableLectures()).willReturn(mockLectures);

        // when
        List<Lecture> availableLectures = lectureService.getAvailableLecture();

        // then
        assertEquals(2, availableLectures.size());
        assertEquals("Java 특강", availableLectures.get(0).getTitle());
        assertEquals("Spring 특강", availableLectures.get(1).getTitle());

        log.info("테스트 성공: {}개의 강의가 반환되었습니다.", availableLectures.size());
    }
}
