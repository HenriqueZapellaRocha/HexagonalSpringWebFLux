package com.example.mongospringwebflux.infrastructure.exception;





public class NotFoundException extends RuntimeException {

  public NotFoundException(String message) {
        super(message);
    }
}
