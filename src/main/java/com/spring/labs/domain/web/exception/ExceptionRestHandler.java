package com.spring.labs.domain.web.exception;

import com.spring.labs.util.ResponseData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ExceptionRestHandler {

    @ExceptionHandler(IdNotFoundException.class)
    public ResponseData.ApiResult<?> idNotFoundException(IdNotFoundException e) {
        log.warn("IdNotFoundException={}", e.getMessage());
        return ResponseData.error(e.getMessage(), HttpStatus.NOT_FOUND);
    }

}
