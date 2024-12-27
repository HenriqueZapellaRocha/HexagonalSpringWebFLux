package com.example.mongospringwebflux.v1.controller.DTOS.responses;


import com.example.mongospringwebflux.repository.entity.enums.UserRoles;
import lombok.Builder;



@Builder
public record UserResponseDTO(

        String id,
        String login,
        UserRoles role,
        String storeRelated
) {
}
