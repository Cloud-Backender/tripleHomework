package com.triple.club.common.exception;

import lombok.Getter;

@Getter
public enum ApiExceptionCode {
    NONE (0,""),
    ALREADY_EXIST_REVIEW(100, "해당 장소에 이미 리뷰가 존재합니다."),
    NOT_EXIST_REVIEW(101, "리뷰가 존재하지 않습니다."),
    NOT_EXIST_USER(102, "유저가 존재하지 않습니다."),
    NOT_EXIST_EVENT_TYPE(600, "존재하지 않는 type 입니다."),
    NOT_EXIST_EVENT_ACTION_TYPE(601, "존재하지 않는 action 입니다."),
    SYSTEM_ERROR(599, "시스템 에러")
    ;


    private final int apiCode;
    private final String message;

    ApiExceptionCode(int code, String message) {
        this.apiCode = code;
        this.message = message;
    }
}
