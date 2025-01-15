package com.example.mongospringwebflux.application.service.services.securityServices.interfaces;

import com.example.mongospringwebflux.domain.DTOS.requests.RegisterRequestDTO;
import com.example.mongospringwebflux.domain.DTOS.requests.loginRequestDTO;
import com.example.mongospringwebflux.domain.DTOS.responses.AuthResponseDTO;
import com.example.mongospringwebflux.domain.DTOS.responses.RegisterResponseDTO;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestBody;
import reactor.core.publisher.Mono;

public interface UserServiceI {

    public Mono<RegisterResponseDTO> createUser(RegisterRequestDTO registerRequest, String storeId );

    public Mono<AuthResponseDTO> login(@RequestBody @Valid loginRequestDTO login );
}
