package com.example.mongospringwebflux.exception;





public class NotFoundException extends RuntimeException {

  public NotFoundException(String message) {
        super(message);
    }
}
