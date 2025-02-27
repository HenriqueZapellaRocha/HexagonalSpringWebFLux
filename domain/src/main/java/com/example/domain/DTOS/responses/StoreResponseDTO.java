package com.example.domain.DTOS.responses;


import lombok.Builder;

@Builder
public record StoreResponseDTO(

        String id,
        String name,
        String city,
        String state

) {
}
