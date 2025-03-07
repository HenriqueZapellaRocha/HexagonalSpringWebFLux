package com.example.service.facades.interfaces;

import com.example.domain.DTOS.requests.RegisterRequestDTO;
import com.example.domain.DTOS.responses.RegisterResponseDTO;
import reactor.core.publisher.Mono;


public interface RegisterFacadePort {

    public Mono<RegisterResponseDTO> registerUser(RegisterRequestDTO registerRequest );
}
