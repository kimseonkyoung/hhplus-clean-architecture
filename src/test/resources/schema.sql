CREATE TABLE users (
      user_id BIGINT AUTO_INCREMENT PRIMARY KEY
);

CREATE TABLE lectures (
      lecture_id BIGINT AUTO_INCREMENT PRIMARY KEY,
      title VARCHAR(100) NOT NULL,
      instructor VARCHAR(50) NOT NULL,
      lecture_date DATE,
      capacity INT NOT NULL DEFAULT 30,
      current_enrollment INT DEFAULT 0
);

CREATE TABLE enrollments (
     enrollment_id BIGINT AUTO_INCREMENT PRIMARY KEY,
     user_id BIGINT NOT NULL,
     lecture_id BIGINT NOT NULL,
     enrolled_at TIMESTAMP NOT NULL, -- TIMESTAMP 사용
     CONSTRAINT unique_user_lecture UNIQUE (user_id, lecture_id) -- UNIQUE KEY 대신 CONSTRAINT 사용
);

-- 별도로 INDEX 생성
CREATE INDEX idx_user_id ON enrollments(user_id);
CREATE INDEX idx_lecture_id ON enrollments(lecture_id);
