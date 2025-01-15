package com.example.mongospringwebflux.domain.DTOS.responses;

import lombok.Builder;

@Builder
public record RegisterResponseDTO(

        String login
) {

}
