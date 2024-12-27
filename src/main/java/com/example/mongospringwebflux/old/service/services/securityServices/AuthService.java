package com.example.mongospringwebflux.service.services.securityServices;


import com.example.mongospringwebflux.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;


@Data
@AllArgsConstructor
@Service
public class AuthService implements ReactiveUserDetailsService {

    private final UserRepository userRepository;

    @Override
    public Mono<UserDetails> findByUsername( String username ) {
        return userRepository.findByLogin( username );
    }

}