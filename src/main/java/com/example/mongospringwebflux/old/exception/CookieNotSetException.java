package com.example.mongospringwebflux.exception;

import lombok.Getter;

@Getter
public class CookieNotSetException extends RuntimeException {

    public CookieNotSetException(String message) {
        super(message);
    }
}
