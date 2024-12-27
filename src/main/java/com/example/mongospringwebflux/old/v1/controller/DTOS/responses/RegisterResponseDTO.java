package com.example.mongospringwebflux.v1.controller.DTOS.responses;

import lombok.Builder;

@Builder
public record RegisterResponseDTO(

        String login
) {

}
