package com.example.mongospringwebflux.adapters.inbound.controller;


import com.example.domain.DTOS.requests.RegisterRequestDTO;
import com.example.domain.DTOS.requests.loginRequestDTO;
import com.example.domain.DTOS.responses.AuthResponseDTO;
import com.example.domain.DTOS.responses.RegisterResponseDTO;
import com.example.service.facades.RegisterFacade;
import com.example.service.services.StoreService;
import com.example.service.services.securityServices.UserService;
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

    private UserService userService;
    private StoreService storeService;
    private RegisterFacade registerFacade;

    @PostMapping( "/login" )
    public Mono<AuthResponseDTO> login(@RequestBody @Valid loginRequestDTO login ) {
        return userService.login( login );
    }

    @PostMapping( "/register" )
    public Mono<RegisterResponseDTO> register(@RequestBody @Valid RegisterRequestDTO registerRequest ) {

        return registerFacade.registerUser( registerRequest );
    }

}