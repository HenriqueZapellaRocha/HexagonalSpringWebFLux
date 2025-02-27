package com.example.domain.DTOS.responses;

import lombok.Builder;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Builder
public record ProductResponseDTO(
        String productID,
        String name,
        PriceResponse price,
        String description,
        String store,
        String imageURL
) {


    @Builder
    public record PriceResponse(
            String currency,
            BigDecimal value
    ) {
        public PriceResponse {
            if (value != null) {
                value = value.setScale( 2, RoundingMode.HALF_UP );
            }
        }
    }
}
