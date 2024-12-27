package com.example.mongospringwebflux.infrastructure.exception.dtos;



import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class CookieNotSetExceptionDTO {


    private String error;
}
