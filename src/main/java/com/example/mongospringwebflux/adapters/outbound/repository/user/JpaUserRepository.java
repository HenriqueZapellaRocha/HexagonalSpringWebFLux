package com.example.mongospringwebflux.adapters.outbound.repository.user;

import com.example.mongospringwebflux.adapters.outbound.repository.entities.UserEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.security.core.userdetails.UserDetails;
import reactor.core.publisher.Mono;

public interface JpaUserRepository extends ReactiveMongoRepository< UserEntity, String>  {

    Mono<UserDetails> findByLogin(String login );
}
