package com.hanghae.lecture.application;

import com.hanghae.lecture.application.exception.LectureNotFoundException;
import com.hanghae.lecture.domain.Lecture;
import com.hanghae.lecture.domain.LectureRegistration;
import com.hanghae.lecture.domain.LectureRegistrationRepository;
import com.hanghae.lecture.domain.LectureRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LectureRegistrationServiceImpl implements LectureRegistrationService {

    private final LectureRegistrationRepository lectureRegistrationRepository;
    private final LectureRepository lectureRepository;

    public LectureRegistrationServiceImpl(LectureRegistrationRepository lectureRegistrationRepository, LectureRepository lectureRepository) {
        this.lectureRegistrationRepository = lectureRegistrationRepository;
        this.lectureRepository = lectureRepository;
    }

    //특강 신청
    @Override
    @Transactional
    public void applyLecture(LectureRegistration registration) {
        // 1. 중복 신청 여부 확인
        boolean alreadyApplyYn = lectureRegistrationRepository.existsByUserIdAndLectureId(
                registration.getUserId(),
                registration.getLectureId()
        );
        registration.validateUniqueRegistration(alreadyApplyYn);

        // 2. lecture 정보 조회
        Lecture lecture = lectureRepository.findByIdWithLock(registration.getLectureId())
                .orElseThrow(() -> new LectureNotFoundException("해당 강의가 존재하지 않습니다."));

        // 3. 정원 초과 여부 확인
        lecture.validateCapacity();

        // 4. 신청 데이터 저장
        lectureRegistrationRepository.save(registration);

        // 5. 신청 인원 증가
        lecture.incrementEnrollment();
        lectureRepository.save(lecture);
    }

    /**
     * 특정 사용자가 신청 완료한 강의 목록을 반환합니다.
     *
     * @param userId 강의를 신청한 사용자의 ID
     * @return 사용자가 신청 완료한 강의 목록 (Lecture 리스트)
     */
    @Override
    public List<Lecture> getCompletedLectures(Long userId) {
        return lectureRegistrationRepository.findCompletedleLectures(userId);
    }
}
