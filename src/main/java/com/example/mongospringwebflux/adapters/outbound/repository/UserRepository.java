package com.example.mongospringwebflux.adapters.outbound.repository;

import com.example.mongospringwebflux.adapters.outbound.repository.entity.UserEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.security.core.userdetails.UserDetails;
import reactor.core.publisher.Mono;

public interface UserRepository extends ReactiveMongoRepository< UserEntity, String>  {

    Mono<UserDetails> findByLogin( String login );
}
