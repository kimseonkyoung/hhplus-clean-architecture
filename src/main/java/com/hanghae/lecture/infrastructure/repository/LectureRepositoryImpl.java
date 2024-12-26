package com.hanghae.lecture.infrastructure.repository;

import com.hanghae.lecture.domain.Lecture;
import com.hanghae.lecture.domain.LectureRepository;
import com.hanghae.lecture.infrastructure.orm.JpaLectureRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public class LectureRepositoryImpl implements LectureRepository {

    private final JpaLectureRepository jpaLectureRepository;

    public LectureRepositoryImpl(JpaLectureRepository jpaLectureRepository) {
        this.jpaLectureRepository = jpaLectureRepository;
    }

    @Override
    @Transactional
    public Optional<Lecture> findByIdWithLock(Long lectureId) {
        return jpaLectureRepository.findByIdWithLock(lectureId);
    }

    @Override
    public void save(Lecture lecture) {
        jpaLectureRepository.save(lecture);
    }

    @Override
    public List<Lecture> findAvailableLectures() {
        return jpaLectureRepository.findAvailableLectures();
    }
}
