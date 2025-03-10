package com.example.service.services.securityServices;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.outbound.entities.UserEntity;
import com.example.service.ports.securityPorts.TokenServicePort;
import com.example.domain.DTOS.exceptions.GlobalException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenServiceAdapter implements TokenServicePort {

    public Mono<String> generateToke( UserEntity userEntity ) {
        return Mono.defer( () -> {
            try {
                Algorithm algorithm = Algorithm.HMAC256("VERYCRAZY");
                String token = JWT.create()
                        .withIssuer( "aut.api" )
                        .withSubject( userEntity.getLogin() )
                        .withExpiresAt( LocalDateTime.now().plusHours(2)
                                .toInstant(ZoneOffset.of("-03:00") ) )
                        .sign( algorithm );

                return Mono.just(token);
            } catch (Exception e) {
                return Mono.error( new GlobalException(e.getMessage()));
            }
        });
    }


    public Mono<String> validateToke( String token ) {
        return Mono.defer(() -> {
            try {
                Algorithm algorithm = Algorithm.HMAC256("VERYCRAZY");
                DecodedJWT jwt = JWT.require(algorithm)
                        .withIssuer("aut.api")
                        .build()
                        .verify(token);

                String login = jwt.getSubject();
                return Mono.just(login);
            } catch (Exception e) {
                return Mono.error( new RuntimeException("Error to validate token", e) );
            }
        });
    }
}

