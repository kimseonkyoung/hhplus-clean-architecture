package com.hanghae.lecture.domain;
import com.hanghae.lecture.domain.exception.DuplicateRegistrationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)  // Mockito 확장을 활성화
public class LectureRegistrationTest {

    @Test
    @DisplayName("팩토리 메서드 - 객체 생성 성공")
    void test1() {
        LocalDateTime enrolledAt = LocalDateTime.now();

        LectureRegistration registration = LectureRegistration.create(1L, 2L, enrolledAt);

        assertNotNull(registration);
        assertEquals(1L, registration.getUserId());
        assertEquals(2L, registration.getLectureId());
        assertEquals(enrolledAt, registration.getEnrolledAt());
    }

    @Test
    @DisplayName("팩토리 메서드 - userId: null / 객체 생성 실패")
    void test2() {
        LocalDateTime enrolledAt = LocalDateTime.now();

        IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                () -> LectureRegistration.create(null, 2L, enrolledAt)
        );

        assertEquals("유저와 신청특강은 null일 수 없습니다.", exception.getMessage());
    }

    @Test
    @DisplayName("팩토리 메서드 - lectureId: null / 객체 생성 실패")
    void test3() {
        LocalDateTime enrolledAt = LocalDateTime.now();

        IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                () -> LectureRegistration.create(1L, null, enrolledAt)
        );

        assertEquals("유저와 신청특강은 null일 수 없습니다.", exception.getMessage());
    }

    @Test
    @DisplayName("중복 신청 검증 - 성공")
    void test4() {
        LectureRegistration registration = new LectureRegistration();

        assertDoesNotThrow(() -> registration.validateUniqueRegistration(false));
    }

    @Test
    @DisplayName("중복 신청 검증 - 중복: 예외 발생")
    void test5() {
        LectureRegistration registration = new LectureRegistration();

        DuplicateRegistrationException exception = assertThrows(
                DuplicateRegistrationException.class,
                () -> registration.validateUniqueRegistration(true)
        );

        assertEquals("이미 신청 성공한 강의는 재신청 할 수 없습니다.", exception.getMessage());
    }
}