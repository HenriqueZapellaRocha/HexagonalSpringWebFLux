package com.example.mongospringwebflux.application.service.services.securityServices;

import com.example.mongospringwebflux.application.service.services.securityServices.interfaces.AuthServiceI;
import com.example.mongospringwebflux.domain.user.UserRepositoryI;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;


@Data
@AllArgsConstructor
@Service
public class AuthService implements ReactiveUserDetailsService, AuthServiceI {

    private final UserRepositoryI userRepository;

    @Override
    public Mono<UserDetails> findByUsername( String username ) {
        return userRepository.findByLogin( username );
    }

}