package com.triple.club.common.exception.handler;

import com.triple.club.common.exception.CustomException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({CustomException.class})
    public ResponseEntity<String> handleYOPLEServiceException(CustomException ex) {
        return new ResponseEntity<>(ex.getErrorDetail(), HttpStatus.OK);
    }

}
