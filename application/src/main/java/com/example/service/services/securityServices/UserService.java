package com.example.service.services.securityServices;

import com.example.domain.DTOS.requests.RegisterRequestDTO;
import com.example.domain.DTOS.requests.loginRequestDTO;
import com.example.domain.DTOS.responses.AuthResponseDTO;
import com.example.domain.DTOS.responses.RegisterResponseDTO;
import com.example.domain.user.User;
import com.example.domain.user.UserRepositoryI;
import com.example.service.services.securityServices.interfaces.TokenServiceI;
import com.example.service.services.securityServices.interfaces.UserServiceI;
import com.example.outbound.repository.entities.UserEntity;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import reactor.core.publisher.Mono;

@Data
@AllArgsConstructor
@Service
public class UserService implements UserServiceI {

    private UserRepositoryI userRepository;
    private ReactiveAuthenticationManager authenticationManager;
    private TokenServiceI tokenService;

    public Mono<RegisterResponseDTO> createUser( RegisterRequestDTO registerRequest, String storeId ) {
        return Mono.just( registerRequest )
                .map( registerRequestDTO -> new BCryptPasswordEncoder().encode( registerRequestDTO.password() ))
                .flatMap( password -> {

                    User newUser = User.builder()
                            .login( registerRequest.login() )
                            .password(password)
                            .role( registerRequest.role() )
                            .storeId( storeId )
                            .build();

                    return userRepository.save( newUser )
                            .onErrorResume( e -> Mono.error( new BadCredentialsException( "user already exist" ) ) );
                } )
                .map( savedUser -> new RegisterResponseDTO( savedUser.getLogin() ));
    }


    public Mono<AuthResponseDTO> login( @RequestBody @Valid loginRequestDTO login ) {
        var usernamePassword = new UsernamePasswordAuthenticationToken( login.login(), login.password() );

        return authenticationManager.authenticate( usernamePassword )
                .flatMap(authentication -> {
                    UserEntity user = (UserEntity) authentication.getPrincipal();

                    return tokenService.generateToke(user)
                            .map(token -> new AuthResponseDTO(token, user.getLogin()));
                })
                .onErrorResume( ex -> Mono.error( new BadCredentialsException( "Invalid username or password" ) ));
    }
}
