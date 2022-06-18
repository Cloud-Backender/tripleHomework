package com.triple.club.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CustomException extends Exception {

    private final ApiExceptionCode apiExceptionCode;

    public CustomException(ApiExceptionCode apiExceptionCode) {
        this.apiExceptionCode = apiExceptionCode;
    }

    public String getErrorDetail() {
        return "Error Code:"+getApiExceptionCode().getApiCode()+"\nError Message:"+getApiExceptionCode().getMessage();
    }
}
