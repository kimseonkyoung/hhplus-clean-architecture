package com.hanghae.lecture.application;

import com.hanghae.lecture.domain.Lecture;
import com.hanghae.lecture.domain.LectureRegistration;
import com.hanghae.lecture.interfaces.api.dto.LectureRegistrationDto;
import com.hanghae.lecture.interfaces.api.dto.LectureResponseDto;
import com.hanghae.lecture.interfaces.api.mapper.LectureMapper;
import com.hanghae.lecture.interfaces.api.mapper.LectureRegistrationMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LectureServiceFacade  {

    private final LectureService lectureService;
    private final LectureRegistrationService lectureRegistrationService;
    private final LectureRegistrationMapper lectureRegistrationMapper;

    public LectureServiceFacade(LectureService lectureService, LectureRegistrationService lectureRegistrationService, LectureRegistrationMapper lectureRegistrationMapper1) {
        this.lectureService = lectureService;
        this.lectureRegistrationService = lectureRegistrationService;
        this.lectureRegistrationMapper = lectureRegistrationMapper1;
    }

    /**
     * 주어진 강의 Lecture ID와 DTO를 사용하여 강의를 신청합니다.
     * @param lectureId 신청하고자 하는 특강 ID
     * @param dto 신청한 유저의 정보가 포함된 DTO
     * @author [kimseonkyoung]
     */
    public void applyLecture(Long lectureId, LectureRegistrationDto dto) {
        // DTO -> LectureRegistration Entity 로 변환
        LectureRegistration registration = lectureRegistrationMapper.toEntity(lectureId, dto);
        lectureRegistrationService.applyLecture(registration);
    }


    /**
     * 현재 신청 가능한(정원이 초과되지 않은) 강의 목록을 보여줍니다.
     * @return 신청 가능한 특강 목록 (LectureResponseDto)
     * @author [kimseonkyoung]
     */
    public List<LectureResponseDto> getAvailableLectures() {
        List<Lecture> lectures = lectureService.getAvailableLecture();
        return LectureMapper.toDtoList(lectures);
    }

    /**
     * 현재 신청 완료된 특강 목록을 보여줍니다.
     * @return 신청 완료된 특강 목록 (LectureResponseDto)
     * @author [kimseonkyoung]
     */
    public List<LectureResponseDto> getCompletedLectures(Long userId) {
        List<Lecture> completedLectures  = lectureRegistrationService.getCompletedLectures(userId);
        return LectureMapper.toDtoList(completedLectures );
    }
}
