package com.example.mongospringwebflux.infrastructure.configs.security;


import com.example.mongospringwebflux.adapters.outbound.repository.user.JpaUserRepository;
import com.example.mongospringwebflux.application.service.services.securityServices.TokenService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Data
@AllArgsConstructor
@Component
public class SecurityFilter implements WebFilter {

    private TokenService tokenService;
    private JpaUserRepository jpaUserRepository;

    @NotNull
    @Override
    public Mono<Void> filter( @NotNull ServerWebExchange exchange,
                              @NotNull WebFilterChain chain ) {

        String token = this.recoverToken( exchange );

        if ( token != null ) {
            return tokenService.validateToke( token )
                    .flatMap( login -> jpaUserRepository.findByLogin( login )
                            .flatMap( user -> {
                                var authentication = new UsernamePasswordAuthenticationToken( user, null,
                                                                                            user.getAuthorities() );
                                return chain.filter( exchange )
                                        .contextWrite( ReactiveSecurityContextHolder
                                                .withAuthentication( authentication ));
                            })
                    )
                    .onErrorResume( e -> chain.filter( exchange ) );
        }

        return chain.filter( exchange );
    }

    private String recoverToken( ServerWebExchange exchange ) {
        String authHeader = exchange.getRequest().getHeaders().getFirst( "Authorization" );
        if ( authHeader == null ) return null;
        return authHeader.replace("Bearer ", "");
    }
}


