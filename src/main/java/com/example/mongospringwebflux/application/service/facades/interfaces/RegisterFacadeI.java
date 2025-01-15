package com.example.mongospringwebflux.application.service.facades.interfaces;

import com.example.mongospringwebflux.domain.DTOS.requests.RegisterRequestDTO;
import com.example.mongospringwebflux.domain.DTOS.responses.RegisterResponseDTO;
import reactor.core.publisher.Mono;


public interface RegisterFacadeI {

    public Mono<RegisterResponseDTO> registerUser(RegisterRequestDTO registerRequest );
}
