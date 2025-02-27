package com.example.domain.DTOS.requests;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Builder
public record ProductRequestDTO(
        @NotBlank( message = "name: blank name" )
        String name,
        @NotNull( message = "price: blank price" )
        @Min( value = 0, message = "price: negative number" )
        BigDecimal price,
        @NotBlank( message = "description: blank description" )
        String description
) {
    public ProductRequestDTO {
        if ( price != null ) {
            price = price.setScale( 2, RoundingMode.HALF_UP );
        }
    }

}

