package com.example.domain.DTOS.responses;


public record AuthResponseDTO(
        String token,
        String username
) {
}
