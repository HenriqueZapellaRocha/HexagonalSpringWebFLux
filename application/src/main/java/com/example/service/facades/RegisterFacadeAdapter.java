package com.example.service.facades;

import com.example.domain.DTOS.requests.RegisterRequestDTO;
import com.example.domain.DTOS.responses.RegisterResponseDTO;
import com.example.domain.enums.UserRoles;
import com.example.service.facades.interfaces.RegisterFacadePort;
import com.example.service.ports.StoreServicePort;
import com.example.service.ports.securityPorts.UserServicePort;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@Data
@AllArgsConstructor
public class RegisterFacadeAdapter implements RegisterFacadePort {

    private final StoreServicePort storeService;
    private final UserServicePort userService;

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
