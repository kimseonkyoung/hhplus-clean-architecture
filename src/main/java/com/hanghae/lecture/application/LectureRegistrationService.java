package com.hanghae.lecture.application;

import com.hanghae.lecture.domain.Lecture;
import com.hanghae.lecture.domain.LectureRegistration;

import java.util.List;

public interface LectureRegistrationService {
    void applyLecture(LectureRegistration lectureRegistration);

    List<Lecture> getCompletedLectures(Long userId);
}
