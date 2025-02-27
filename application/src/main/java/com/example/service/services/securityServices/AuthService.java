package com.example.service.services.securityServices;

import com.example.domain.user.UserRepositoryI;
import com.example.service.services.securityServices.interfaces.AuthServiceI;
import com.example.mappers.UserMappers;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;


@Data
@AllArgsConstructor
@Service
public class AuthService implements ReactiveUserDetailsService, AuthServiceI {

    private final UserRepositoryI userRepository;
    private final UserMappers userMappers;

    @Override
    public Mono<UserDetails> findByUsername( String username ) {
        return userRepository.findByLogin( username )
                .map( userMappers::DomainToEntity );
    }

}