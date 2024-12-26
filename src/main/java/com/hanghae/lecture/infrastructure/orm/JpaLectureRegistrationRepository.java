package com.hanghae.lecture.infrastructure.orm;

import com.hanghae.lecture.domain.Lecture;
import com.hanghae.lecture.domain.LectureRegistration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface JpaLectureRegistrationRepository extends JpaRepository<LectureRegistration, Long> {

    boolean existsByUserIdAndLectureId(Long userId, Long lectureId);

    @Query("SELECT r FROM LectureRegistration r  where r.userId = :userId")
    List<Lecture> findCompletedleLectures(@Param("userId") Long userId);
}
