package com.example.mongospringwebflux.application.service.services.securityServices.interfaces;

import org.springframework.security.core.userdetails.UserDetails;
import reactor.core.publisher.Mono;

public interface AuthServiceI {

    public Mono<UserDetails> findByUsername(String username );
}
