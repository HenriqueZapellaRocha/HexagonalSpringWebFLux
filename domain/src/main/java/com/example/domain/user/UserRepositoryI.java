package com.example.domain.user;


import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserRepositoryI {

    Mono<User> findByLogin( String login );
    Mono<User> findById( String id );
    Mono<User> save( User user );
    Flux<User> findAll();
    Mono<Void> delete( User user );
}
