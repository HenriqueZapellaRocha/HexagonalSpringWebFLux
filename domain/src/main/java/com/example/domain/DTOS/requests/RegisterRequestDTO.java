package com.example.domain.DTOS.requests;

import com.example.domain.enums.UserRoles;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record RegisterRequestDTO(
        @NotBlank( message = "login: blank login" )
        String login,
        @NotBlank( message = "password: blank pasword" )
        String password,
        @NotNull( message = "role: role is blank" )
        UserRoles role,

        @Valid
        StoreCreationRequestDTO storeRelated

) {
}
