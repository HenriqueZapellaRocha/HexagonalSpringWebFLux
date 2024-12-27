package com.example.mongospringwebflux.adapters.inbound.controller.DTOS.responses;


import com.example.mongospringwebflux.adapters.outbound.repository.entity.enums.UserRoles;
import lombok.Builder;



@Builder
public record UserResponseDTO(

        String id,
        String login,
        UserRoles role,
        String storeRelated
) {
}
