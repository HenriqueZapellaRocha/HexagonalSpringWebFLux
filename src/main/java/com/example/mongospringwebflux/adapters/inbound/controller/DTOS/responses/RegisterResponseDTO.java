package com.example.mongospringwebflux.adapters.inbound.controller.DTOS.responses;

import lombok.Builder;

@Builder
public record RegisterResponseDTO(

        String login
) {

}
