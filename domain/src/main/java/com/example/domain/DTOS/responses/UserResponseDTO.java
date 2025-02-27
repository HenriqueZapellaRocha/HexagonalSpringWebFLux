package com.example.domain.DTOS.responses;


import com.example.domain.enums.UserRoles;
import lombok.Builder;



@Builder
public record UserResponseDTO(

        String id,
        String login,
        UserRoles role,
        String storeRelated
) {
}
