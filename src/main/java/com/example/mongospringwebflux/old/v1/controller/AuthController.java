package com.example.mongospringwebflux.v1.controller;


import com.example.mongospringwebflux.service.facades.RegisterFacade;
import com.example.mongospringwebflux.service.services.StoreService;
import com.example.mongospringwebflux.service.services.securityServices.UserService;
import com.example.mongospringwebflux.v1.controller.DTOS.responses.AuthResponseDTO;
import com.example.mongospringwebflux.v1.controller.DTOS.requests.RegisterRequestDTO;
import com.example.mongospringwebflux.v1.controller.DTOS.requests.loginRequestDTO;
import com.example.mongospringwebflux.v1.controller.DTOS.responses.RegisterResponseDTO;
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
    public Mono<AuthResponseDTO> login( @RequestBody @Valid loginRequestDTO login ) {
        return userService.login( login );
    }

    @PostMapping( "/register" )
    public Mono<RegisterResponseDTO> register( @RequestBody @Valid RegisterRequestDTO registerRequest ) {

        return registerFacade.registerUser( registerRequest );
    }

}