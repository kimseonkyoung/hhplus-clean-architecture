package com.hanghae.lecture.infrastructure.repository;

import com.hanghae.lecture.domain.Lecture;
import com.hanghae.lecture.domain.LectureRegistration;
import com.hanghae.lecture.domain.LectureRegistrationRepository;
import com.hanghae.lecture.infrastructure.orm.JpaLectureRegistrationRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class LectureRegistrationRepositoryImpl implements LectureRegistrationRepository {

    private final JpaLectureRegistrationRepository jpaLectureRegistrationRepository;

    public LectureRegistrationRepositoryImpl(JpaLectureRegistrationRepository jpaLectureRegistrationRepository) {
        this.jpaLectureRegistrationRepository = jpaLectureRegistrationRepository;
    }

    @Override
    public void save(LectureRegistration lectureRegistration) {
        jpaLectureRegistrationRepository.save(lectureRegistration);
    }

    @Override
    public boolean existsByUserIdAndLectureId(Long userId, Long lectureId) {
        // 사용자가 중복 신청했는지 조회
        return jpaLectureRegistrationRepository.existsByUserIdAndLectureId(userId, lectureId);
    }

    @Override
    public List<Lecture> findCompletedleLectures(Long userId) {
        return jpaLectureRegistrationRepository.findCompletedleLectures(userId);
    }
}
