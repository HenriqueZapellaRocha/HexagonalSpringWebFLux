package com.example.service.services.securityServices.interfaces;


import com.example.entities.UserEntity;
import reactor.core.publisher.Mono;

public interface TokenServiceI {

    public Mono<String> generateToke(UserEntity userEntity);

    public Mono<String> validateToke(String token);
}
