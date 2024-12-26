package com.hanghae.lecture.infrastructure.orm;

import com.hanghae.lecture.domain.Lecture;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface JpaLectureRepository extends JpaRepository<Lecture, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT l FROM Lecture l where l.id = :lectureId")
    Optional<Lecture> findByIdWithLock(@Param("lectureId") Long lectureId);

    @Query("SELECT l FROM Lecture l where l.currentEnrollment < l.capacity")
    List<Lecture> findAvailableLectures();
}
