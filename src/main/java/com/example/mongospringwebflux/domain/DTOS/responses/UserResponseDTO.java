package com.example.mongospringwebflux.domain.DTOS.responses;


import com.example.mongospringwebflux.domain.enums.UserRoles;
import lombok.Builder;



@Builder
public record UserResponseDTO(

        String id,
        String login,
        UserRoles role,
        String storeRelated
) {
}
