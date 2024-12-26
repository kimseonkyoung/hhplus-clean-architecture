package com.hanghae.lecture.application;

import com.hanghae.lecture.domain.Lecture;
import org.springframework.stereotype.Service;

import java.util.List;


public interface LectureService {
    List<Lecture> getAvailableLecture();
}
