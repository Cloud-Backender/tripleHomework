package com.triple.club.common.exception;

import com.triple.club.common.model.dto.ApiExceptionDto;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CustomException extends Exception {

    private final ApiExceptionCode apiExceptionCode;

    public CustomException(ApiExceptionCode apiExceptionCode) {
        this.apiExceptionCode = apiExceptionCode;
    }

    public ApiExceptionDto getErrorDetail() {
        return new ApiExceptionDto(this.apiExceptionCode);
    }
}
