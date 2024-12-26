package com.hanghae.lecture.domain;

import java.util.List;

public interface LectureRegistrationRepository {
    void save(LectureRegistration lectureRegistration);

    boolean existsByUserIdAndLectureId(Long userId, Long lectureId);

    List<Lecture> findCompletedleLectures(Long userId);
}
