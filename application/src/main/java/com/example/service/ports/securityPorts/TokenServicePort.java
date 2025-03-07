package com.example.service.ports.securityPorts;


import com.example.outbound.entities.UserEntity;
import reactor.core.publisher.Mono;

public interface TokenServicePort {

    public Mono<String> generateToke(UserEntity userEntity);

    public Mono<String> validateToke(String token);
}
