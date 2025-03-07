package com.example.service.ports.securityPorts;

import org.springframework.security.core.userdetails.UserDetails;
import reactor.core.publisher.Mono;

public interface AuthServicePort {

    public Mono<UserDetails> findByUsername( String username );
}
