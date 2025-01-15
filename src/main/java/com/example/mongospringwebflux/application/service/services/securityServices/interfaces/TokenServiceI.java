package com.example.mongospringwebflux.application.service.services.securityServices.interfaces;


import com.example.mongospringwebflux.adapters.outbound.repository.entities.UserEntity;
import reactor.core.publisher.Mono;

public interface TokenServiceI {

    public Mono<String> generateToke(UserEntity userEntity);

    public Mono<String> validateToke(String token);
}
