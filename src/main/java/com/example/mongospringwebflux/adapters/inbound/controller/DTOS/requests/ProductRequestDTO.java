package com.example.mongospringwebflux.adapters.inbound.controller.DTOS.requests;


import com.example.mongospringwebflux.adapters.outbound.repository.entity.ProductEntity;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.UUID;


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

    public ProductEntity toEntity( String id ) {
        return ProductEntity.builder()
                .productID(id)
                .name( this.name )
                .price( this.price )
                .description( this.description )
                .build();
    }

    public ProductEntity toEntity() {
        return ProductEntity.builder()
                .productID( UUID.randomUUID().toString() )
                .name( this.name )
                .price( this.price )
                .description( this.description )
                .build();
    }
}

