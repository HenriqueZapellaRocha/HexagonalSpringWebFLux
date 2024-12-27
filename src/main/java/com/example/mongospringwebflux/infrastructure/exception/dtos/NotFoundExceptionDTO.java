package com.example.mongospringwebflux.infrastructure.exception.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor


public class NotFoundExceptionDTO {


    private String error;
}
