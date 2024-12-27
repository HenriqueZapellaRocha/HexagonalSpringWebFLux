package com.example.mongospringwebflux.adapters.inbound.controller.DTOS.responses;


public record AuthResponseDTO(
        String token,
        String username
) {
}
