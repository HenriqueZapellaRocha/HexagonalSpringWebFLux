package com.example.mongospringwebflux.domain.user;


import com.example.mongospringwebflux.adapters.outbound.repository.entities.UserEntity;
import org.springframework.security.core.userdetails.UserDetails;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserRepositoryI {

    Mono<UserDetails> findByLogin( String login );
    Mono<UserEntity> findById( String id );
    Mono<User> save( User user );
    Flux<User> findAll();
}
