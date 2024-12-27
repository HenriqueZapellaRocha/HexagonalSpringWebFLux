package com.example.mongospringwebflux.exception;


import lombok.Getter;

@Getter
public class GlobalException extends Exception {

    public GlobalException(String message) {
        super(message);
    }
}
