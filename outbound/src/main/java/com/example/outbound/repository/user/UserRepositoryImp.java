package com.example.outbound.repository.user;


import com.example.domain.user.User;
import com.example.domain.user.UserRepositoryI;
import com.example.outbound.mappers.UserMappers;
import lombok.Data;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Data
@Repository
public class UserRepositoryImp implements UserRepositoryI {

    private final JpaUserRepository userRepository;
    private final UserMappers userMappers;

    @Override
    public Mono<User> findByLogin(String login ) {
        return userRepository.findByLogin( login );
    }

    @Override
    public Mono<User> findById( String id ) {
        return userRepository.findById( id )
                .map(userMappers::EntityToDomain);
    }

    @Override
    public Mono<User> save( User user ) {
        return userRepository.save( userMappers.DomainToEntity( user ) )
                                        .map(userMappers::EntityToDomain);
    }

    @Override
    public Flux<User> findAll() {
        return userRepository.findAll().map( userMappers::EntityToDomain );
    }

    @Override
    public Mono<Void> delete(User user ) {
        return userRepository.delete( userMappers.DomainToEntity( user ) );
    }

}
