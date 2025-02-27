package com.example.outbound.repository.user;

import com.example.domain.user.User;
import com.example.outbound.repository.entities.UserEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface JpaUserRepository extends ReactiveMongoRepository< UserEntity, String>  {

    Mono<User> findByLogin(String login );
}
