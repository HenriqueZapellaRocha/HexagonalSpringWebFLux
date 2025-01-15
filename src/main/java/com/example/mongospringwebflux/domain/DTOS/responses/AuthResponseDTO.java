package com.example.mongospringwebflux.domain.DTOS.responses;


public record AuthResponseDTO(
        String token,
        String username
) {
}
