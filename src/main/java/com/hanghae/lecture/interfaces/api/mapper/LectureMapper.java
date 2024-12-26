package com.hanghae.lecture.interfaces.api.mapper;

import com.hanghae.lecture.domain.Lecture;
import com.hanghae.lecture.interfaces.api.dto.LectureResponseDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class LectureMapper {

    // Entity -> DTO 변환
    public static LectureResponseDto toDto(Lecture entity){
        return new LectureResponseDto(
                entity.getId(),
                entity.getTitle(),
                entity.getInstructor(),
                entity.getLectureDate()
        );
    }

    // Entity 리스트 -> DTO 리스트 변환
    public static List<LectureResponseDto> toDtoList(List<Lecture> entities){
        return entities.stream()
                .map(LectureMapper::toDto)
                .collect(Collectors.toList());
    }


}
