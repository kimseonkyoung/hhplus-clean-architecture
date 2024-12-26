package com.hanghae.lecture.integration;

import com.hanghae.lecture.application.LectureRegistrationService;
import com.hanghae.lecture.application.exception.LectureNotFoundException;
import com.hanghae.lecture.domain.Lecture;
import com.hanghae.lecture.domain.LectureRegistration;
import com.hanghae.lecture.domain.LectureRepository;
import com.hanghae.lecture.domain.exception.DuplicateRegistrationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class LectureApplicationIntergrationTest {

    @Autowired
    private LectureRegistrationService lectureRegistrationService;

    @Autowired
    private LectureRepository lectureRepository;

    @DisplayName("동시_특강_신청_테스트 - 30명 정원 특강 40명 신청시 30명만 통과 테스트")
    @Test
    void test1() throws InterruptedException {
        //given
        Lecture lecture = Lecture.create(
                1L,
                "테스트 강의",
                "홍길동",
                LocalDateTime.now().toLocalDate(),
                30, // 정원 30명
                0  // 현재 신청자 수 28명
        );
        lectureRepository.save(lecture);

        int concurrentUsers = 40; // 동시 요청 수
        ExecutorService executorService = Executors.newFixedThreadPool(concurrentUsers);
        CountDownLatch latch = new CountDownLatch(concurrentUsers);

        for (int i = 1; i <= concurrentUsers; i++) {
            final long userId = i;
            executorService.submit(() -> {
                try {
                    LectureRegistration registration = LectureRegistration.create(
                            userId,
                            lecture.getId(),
                            LocalDateTime.now()
                    );
                    lectureRegistrationService.applyLecture(registration);
                    System.out.println("사용자 " + userId + " 신청 성공");
                } catch (Exception e) {
                    System.err.println("사용자 " + userId + " 신청 실패: " + e.getMessage());
                } finally {
                    latch.countDown();  // 완료 시 감소
                }
            });
        }
        latch.await();  // 모든 작업 종료 대기
        executorService.shutdown();
        executorService.awaitTermination(10, TimeUnit.SECONDS);

        // 트랜잭션을 다시 시작하여 데이터 확인
        Lecture updatedLecture = lectureRepository.findByIdWithLock(lecture.getId())
                .orElseThrow(() -> new LectureNotFoundException("해당 강의를 찾을 수 없습니다."));
        assertThat(updatedLecture.getCurrentEnrollment()).isEqualTo(30);
    }

    @DisplayName("\"동시_특강_신청_테스트 - 28명 정원 특강 40명 신청시 2명만 통과 테스트")
    @Test
    void test2() throws InterruptedException {
        //given
        Lecture lecture = Lecture.create(
                1L,
                "테스트 강의",
                "홍길동",
                LocalDateTime.now().toLocalDate(),
                30, // 정원 30명
                28  // 현재 신청자 수 28명
        );
        lectureRepository.save(lecture);


        int concurrentUsers = 40; // 동시 요청 수
        ExecutorService executorService = Executors.newFixedThreadPool(concurrentUsers);
        CountDownLatch latch = new CountDownLatch(concurrentUsers);

        for (int i = 1; i <= concurrentUsers; i++) {
            final long userId = i;
            executorService.submit(() -> {
                try {
                    LectureRegistration registration = LectureRegistration.create(
                            userId,
                            lecture.getId(),
                            LocalDateTime.now()
                    );
                    lectureRegistrationService.applyLecture(registration);
                    System.out.println("사용자 " + userId + " 신청 성공");
                } catch (Exception e) {
                    System.err.println("사용자 " + userId + " 신청 실패: " + e.getMessage());
                } finally {
                    latch.countDown();  // 완료 시 감소
                }
            });
        }
        latch.await();  // 모든 작업 종료 대기
        executorService.shutdown();
        executorService.awaitTermination(10, TimeUnit.SECONDS);

        // 트랜잭션을 다시 시작하여 데이터 확인
        Lecture updatedLecture = lectureRepository.findByIdWithLock(lecture.getId())
                .orElseThrow(() -> new LectureNotFoundException("해당 강의를 찾을 수 없습니다."));
        assertThat(updatedLecture.getCurrentEnrollment()).isEqualTo(30);
    }

    @DisplayName("중복신청 검증 테스트")
    @Test
    void test3() {
        //given
        Lecture lecture = Lecture.create(
                1L,
                "테스트 강의",
                "홍길동",
                LocalDateTime.now().toLocalDate(),
                30, // 정원 30명
                28  // 현재 신청자 수 28명
        );
        lectureRepository.save(lecture);

        LectureRegistration registration = LectureRegistration.create(
                1L,
                lecture.getId(),
                LocalDateTime.now()
        );

        // 첫 번째 신청은 정상 처리
        lectureRegistrationService.applyLecture(registration);

        assertThrows(DuplicateRegistrationException.class, () -> {
            lectureRegistrationService.applyLecture(registration);
        }, "이미 신청 성공한 강의는 재신청 할 수 없습니다.");
    }
}
