package com.hanghae.lecture.domain;

import com.hanghae.lecture.domain.exception.DuplicateRegistrationException;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Table(name = "enrollments",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"lecture_id", "user_id"})})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LectureRegistration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long enrollment_id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "lecture_id", nullable = false)
    private Long lectureId;

    @Column(name = "enrolled_at", nullable = false)
    private LocalDateTime enrolledAt;

    // 생성자를 팩토리 메서드로 생성
    public static LectureRegistration create(Long userId, Long lectureId, LocalDateTime enrolledAt) {
        if (userId == null || lectureId == null) {
            throw new IllegalStateException("유저와 신청특강은 null일 수 없습니다.");
        }
        return new LectureRegistration(null, userId, lectureId, enrolledAt);
    }

    // 중복 신청 여부 검증
    public void validateUniqueRegistration(boolean alreadyRegistered) {
        if (alreadyRegistered) {
            throw new DuplicateRegistrationException("이미 신청 성공한 강의는 재신청 할 수 없습니다.");
        }
    }
}
