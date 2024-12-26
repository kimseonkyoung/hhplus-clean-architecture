package com.hanghae.lecture.interfaces.api;

import com.hanghae.lecture.application.LectureServiceFacade;
import com.hanghae.lecture.interfaces.api.dto.LectureRegistrationDto;
import com.hanghae.lecture.interfaces.api.dto.LectureResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lectures")
public class LectureApplicationController {

    private final LectureServiceFacade lectureServiceFacade;

    public LectureApplicationController(LectureServiceFacade lectureServiceFacade) {
        this.lectureServiceFacade = lectureServiceFacade;
    }

    // 특강 신청
    @PostMapping("/{lectureId}/apply")
    public ResponseEntity<String> applyLecture(
            @PathVariable Long lectureId,
            @RequestBody LectureRegistrationDto request
            ){

        lectureServiceFacade.applyLecture(lectureId, request);
        return ResponseEntity.ok("특강 신청 완료");
    }

    // 특강 신청 가능 목록 조회
    @GetMapping("/available")
    public ResponseEntity<List<LectureResponseDto>> getAvailableLectures() {
        return ResponseEntity.ok(lectureServiceFacade.getAvailableLectures());
    }

    // 사용자별 신청 완료 목록 조회
    @GetMapping("/completed")
    public ResponseEntity<List<LectureResponseDto>> getCompletedLectures(
            @RequestParam Long userId
    ) {
        return ResponseEntity.ok(lectureServiceFacade.getCompletedLectures(userId));
    }


}
