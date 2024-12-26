package com.hanghae.lecture.interfaces.api.mapper;

import com.hanghae.lecture.domain.LectureRegistration;
import com.hanghae.lecture.interfaces.api.dto.LectureRegistrationDto;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class LectureRegistrationMapper {

    public LectureRegistration toEntity(Long lectureId, LectureRegistrationDto dto) {
        return new LectureRegistration(
                null,
                dto.getUserId(),
                lectureId,
                LocalDateTime.now()
        );
    }
}
