package com.example.mongospringwebflux.application.service.facades;

import com.example.mongospringwebflux.adapters.outbound.repository.entity.enums.UserRoles;
import com.example.mongospringwebflux.application.service.services.StoreService;
import com.example.mongospringwebflux.application.service.services.securityServices.UserService;
import com.example.mongospringwebflux.adapters.inbound.controller.DTOS.requests.RegisterRequestDTO;
import com.example.mongospringwebflux.adapters.inbound.controller.DTOS.responses.RegisterResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@Data
@AllArgsConstructor
public class RegisterFacade {

    private final StoreService storeService;
    private final UserService userService;

    public Mono<RegisterResponseDTO> registerUser(RegisterRequestDTO registerRequest ) {

        if( registerRequest.role() == UserRoles.ROLE_STORE_ADMIN && registerRequest.storeRelated() == null )
            return Mono.error( new BadCredentialsException( "store in null" ));

        if ( registerRequest.role() == UserRoles.ROLE_STORE_ADMIN ) {
            String storeId = UUID.randomUUID().toString();
            return userService.createUser( registerRequest, storeId )
                    .flatMap( userCreated ->  storeService.createStore( registerRequest.storeRelated(), storeId ))
                    .then( Mono.just( new RegisterResponseDTO( registerRequest.login() ) ) );
        } else {
            return userService.createUser( registerRequest, null );
        }
    }
}
