package com.example.mongospringwebflux.v1.controller.DTOS.responses;


public record AuthResponseDTO(
        String token,
        String username
) {
}
