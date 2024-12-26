package com.hanghae.lecture.interfaces;

import com.hanghae.lecture.application.LectureServiceFacade;
import com.hanghae.lecture.interfaces.api.LectureApplicationController;
import com.hanghae.lecture.interfaces.api.dto.LectureRegistrationDto;
import com.hanghae.lecture.interfaces.api.dto.LectureResponseDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.BDDMockito.*;

@WebMvcTest(LectureApplicationController.class)
class LectureApplicationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LectureServiceFacade lectureServiceFacade;

    @Test
    @DisplayName("특강 신청 성공")
    void test1() throws Exception {

        // given
        LectureRegistrationDto request = new LectureRegistrationDto();
        request.setUserId(100L);

        // Mocking void 메서드 동작 설정
        willDoNothing().given(lectureServiceFacade).applyLecture(1L, request);

        // when, then
        mockMvc.perform(post("/api/lectures/{lectureId}/apply", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userId\":100}")) // 요청 데이터
                .andExpect(status().isOk())
                .andExpect(content().string("특강 신청 완료"));
    }

    @Test
    @DisplayName("특강 신청 가능 목록 조회 성공")
    void test2() throws Exception {
        List<LectureResponseDto> mockResponse = Arrays.asList(
                new LectureResponseDto(1L, "Java 특강", "홍자바", LocalDate.of(2024, 12, 31)),
                new LectureResponseDto(2L, "Spring 특강", "이봄", LocalDate.of(2025, 1, 15))
        );

        // given
        given(lectureServiceFacade.getAvailableLectures()).willReturn(mockResponse);

        // when, then
        mockMvc.perform(get("/api/lectures/available")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].lectureId").value(1L))
                .andExpect(jsonPath("$[0].title").value("Java 특강"))
                .andExpect(jsonPath("$[0].instructor").value("홍자바"))
                .andExpect(jsonPath("$[0].lectureDate").value("2024-12-31"));
    }

    @Test
    @DisplayName("사용자별 신청 완료 목록 조회 성공")
    void test3() throws Exception {
        Long userId = 1L;
        List<LectureResponseDto> mockResponse = Arrays.asList(
                new LectureResponseDto(3L, "데이터베이스 특강", "김디비", LocalDate.of(2024, 12, 20)),
                new LectureResponseDto(4L, "알고리즘 특강", "임꺽정", LocalDate.of(2024, 12, 25))
        );

        // given
        given(lectureServiceFacade.getCompletedLectures(userId)).willReturn(mockResponse);

        // when, then
        mockMvc.perform(get("/api/lectures/completed")
                        .param("userId", String.valueOf(userId))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].lectureId").value(3L))
                .andExpect(jsonPath("$[0].title").value("데이터베이스 특강"))
                .andExpect(jsonPath("$[0].instructor").value("김디비"))
                .andExpect(jsonPath("$[0].lectureDate").value("2024-12-20"));
    }
}
