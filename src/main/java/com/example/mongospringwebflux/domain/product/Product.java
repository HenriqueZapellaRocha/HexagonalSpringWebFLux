package com.example.mongospringwebflux.domain.product;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class Product {

    private String productID;

    private String name;
    private BigDecimal price;
    private String description;
    private String storeId;

    @Builder.Default
    private String imageUrl = "has no image";
}
