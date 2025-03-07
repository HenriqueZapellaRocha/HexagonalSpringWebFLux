package com.example.controller;


import com.example.domain.DTOS.requests.RegisterRequestDTO;
import com.example.domain.DTOS.requests.loginRequestDTO;
import com.example.domain.DTOS.responses.AuthResponseDTO;
import com.example.domain.DTOS.responses.RegisterResponseDTO;
import com.example.service.facades.interfaces.RegisterFacadePort;
import com.example.service.ports.securityPorts.UserServicePort;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;


@RestController
@Data
@AllArgsConstructor
@RequestMapping( "/auth" )
public class AuthController {

    private UserServicePort userService;
    private RegisterFacadePort registerFacade;

    @PostMapping( "/login" )
    public Mono<AuthResponseDTO> login( @RequestBody @Valid loginRequestDTO login ) {
        return userService.login( login );
    }

    @PostMapping( "/register" )
    public Mono<RegisterResponseDTO> register( @RequestBody @Valid RegisterRequestDTO registerRequest ) {

        return registerFacade.registerUser( registerRequest );
    }

}