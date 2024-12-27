package com.example.mongospringwebflux.configs.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Data
@AllArgsConstructor
@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    private SecurityFilter securityFilter;

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
                .csrf( ServerHttpSecurity.CsrfSpec::disable )
                .authorizeExchange( auth -> auth
                        .pathMatchers(HttpMethod.POST, "/auth/login").permitAll()
                        .pathMatchers(HttpMethod.POST, "/auth/register").permitAll()

                        .pathMatchers(HttpMethod.POST, "/product/**")
                                                .hasAnyAuthority("ROLE_SUPERVISOR", "ROLE_ADMIN")
                        .pathMatchers(HttpMethod.PUT, "/product/**")
                                                .hasAnyAuthority("ROLE_SUPERVISOR" )
                        .pathMatchers(HttpMethod.DELETE, "/product**")
                                                .hasAnyAuthority("ROLE_SUPERVISOR", "ROLE_ADMIN")
                        .pathMatchers(HttpMethod.GET, "/product**")
                                            .hasAnyAuthority("ROLE_SUPERVISOR", "ROLE_ADMIN", "ROLE_USER")

                        .pathMatchers(HttpMethod.GET, "/store**")
                                            .hasAnyAuthority("ROLE_SUPERVISOR", "ROLE_ADMIN", "ROLE_USER")

                        .pathMatchers("/admin/**")
                                            .hasAuthority("ROLE_ADMIN")

                        .pathMatchers( "/webjars/**", "/swagger-ui/**", "/v3/api-docs/**" )
                                                .permitAll()

                        .anyExchange().authenticated()
                )
                .addFilterBefore( securityFilter, SecurityWebFiltersOrder.AUTHORIZATION )
                .build();
    }

    @Bean
    public ReactiveAuthenticationManager reactiveAuthenticationManager( ReactiveUserDetailsService userDetailsService,
                                                                        PasswordEncoder passwordEncoder) {
        return new UserDetailsRepositoryReactiveAuthenticationManager(userDetailsService) {{
            setPasswordEncoder(passwordEncoder);
        }};
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}


