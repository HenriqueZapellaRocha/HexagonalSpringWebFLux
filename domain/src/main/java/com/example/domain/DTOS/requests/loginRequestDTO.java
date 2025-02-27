package com.example.domain.DTOS.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;


@Builder
public record loginRequestDTO(
        @NotBlank( message = "login: blank login" )
        String login,
        @NotBlank( message = "password: blank password" )
        String password
) {
}
