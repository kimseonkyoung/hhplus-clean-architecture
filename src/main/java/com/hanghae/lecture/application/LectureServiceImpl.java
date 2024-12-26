package com.hanghae.lecture.application;

import com.hanghae.lecture.domain.Lecture;
import com.hanghae.lecture.domain.LectureRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LectureServiceImpl implements LectureService {

    private final LectureRepository lectureRepository;

    public LectureServiceImpl(LectureRepository lectureRepository) {
        this.lectureRepository = lectureRepository;
    }

    /**
     * 특정 사용자가 신청 가능한 강의 목록을 반환합니다.
     *
     * @return 특정 사용자가 신청 가능한 강의 목록 (Lecture 리스트)
     */
    @Override
    public List<Lecture> getAvailableLecture() {
        return lectureRepository.findAvailableLectures();
    }
}
