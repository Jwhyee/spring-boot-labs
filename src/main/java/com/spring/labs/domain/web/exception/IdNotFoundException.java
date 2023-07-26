package com.spring.labs.domain.web.exception;

public class IdNotFoundException extends RuntimeException {
    public IdNotFoundException(String msg) {
        super(msg);
    }
}
