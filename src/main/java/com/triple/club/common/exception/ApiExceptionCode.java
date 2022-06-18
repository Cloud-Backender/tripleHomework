package com.triple.club.common.exception;

import lombok.Getter;

@Getter
public enum ApiExceptionCode {
    NONE (0,""),
    ALREADY_EXIST_REVIEW(100, "이미 리뷰가 존재합니다."),
    NOT_EXIST_REVIEW(100, "리뷰가 존재하지 않습니다."),
    SYSTEM_ERROR(599, "시스템 에러")
    ;


    private final int apiCode;
    private final String message;

    ApiExceptionCode(int code, String message) {
        this.apiCode = code;
        this.message = message;
    }
}