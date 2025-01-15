package com.example.mongospringwebflux.domain.user;


import org.springframework.security.core.userdetails.UserDetails;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserRepositoryI {

    Mono<UserDetails> findByLogin( String login );
    Mono<User> findById( String id );
    Mono<User> save( User user );
    Flux<User> findAll();
    Mono<Void> delete( User user );
}
