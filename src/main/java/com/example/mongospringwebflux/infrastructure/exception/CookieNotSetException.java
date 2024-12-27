package com.example.mongospringwebflux.infrastructure.exception;

import lombok.Getter;

@Getter
public class CookieNotSetException extends RuntimeException {

    public CookieNotSetException(String message) {
        super(message);
    }
}
