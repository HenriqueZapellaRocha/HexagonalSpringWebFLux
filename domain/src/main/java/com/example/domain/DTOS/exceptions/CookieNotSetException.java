package com.example.domain.DTOS.exceptions;

import lombok.Getter;

@Getter
public class CookieNotSetException extends RuntimeException {

    public CookieNotSetException(String message) {
        super(message);
    }
}
