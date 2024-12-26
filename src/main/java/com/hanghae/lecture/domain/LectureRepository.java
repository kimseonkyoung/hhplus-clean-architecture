package com.hanghae.lecture.domain;

import java.util.List;
import java.util.Optional;

public interface LectureRepository {
    Optional<Lecture> findByIdWithLock(Long lectureId);

    void save(Lecture lecture);

    List<Lecture> findAvailableLectures();
}
