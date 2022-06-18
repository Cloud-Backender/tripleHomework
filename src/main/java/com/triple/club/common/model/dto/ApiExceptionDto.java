package com.triple.club.common.model.dto;

import com.triple.club.common.exception.ApiExceptionCode;
import lombok.Getter;

@Getter
public class ApiExceptionDto {
    private final long errorCode;
    private final String errorMessage;

    public ApiExceptionDto(ApiExceptionCode apiExceptionCode) {
        this.errorCode = apiExceptionCode.getApiCode();
        this.errorMessage = apiExceptionCode.getMessage();
    }
}
