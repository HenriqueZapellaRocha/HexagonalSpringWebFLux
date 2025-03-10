package com.example.service.ports.securityPorts;

import com.example.domain.DTOS.requests.RegisterRequestDTO;
import com.example.domain.DTOS.requests.loginRequestDTO;
import com.example.domain.DTOS.responses.AuthResponseDTO;
import com.example.domain.DTOS.responses.RegisterResponseDTO;
import org.springframework.web.bind.annotation.RequestBody;
import reactor.core.publisher.Mono;

public interface UserServicePort {

    public Mono<RegisterResponseDTO> createUser(RegisterRequestDTO registerRequest, String storeId );

    public Mono<AuthResponseDTO> login(@RequestBody loginRequestDTO login );
}
