package com.hanghae.lecture.interfaces.api.dto;

public record ErrorResponse(
        String code,
        String message
) {
}
