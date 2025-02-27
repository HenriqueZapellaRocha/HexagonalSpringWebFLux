package com.example.domain.DTOS.exceptions;


import lombok.Getter;

@Getter
public class GlobalException extends Exception {

    public GlobalException(String message) {
        super(message);
    }
}
