package com.example.mongospringwebflux.utils.mappers;

import com.example.mongospringwebflux.adapters.outbound.repository.entities.ProductEntity;
import com.example.mongospringwebflux.domain.DTOS.requests.ProductRequestDTO;
import com.example.mongospringwebflux.domain.DTOS.responses.ProductResponseDTO;
import com.example.mongospringwebflux.domain.product.Product;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProductMappers {

    public Product EntityToDomain( ProductEntity productEntity ) {
        return Product.builder()
                .productID( productEntity.getProductID() )
                .name( productEntity.getName() )
                .price( productEntity.getPrice() )
                .description( productEntity.getDescription() )
                .storeId( productEntity.getStoreId() )
                .imageUrl( productEntity.getImageUrl() )
                .build();
    }

    public ProductEntity DomainToEntity( Product product ) {
        return ProductEntity.builder()
                .productID( product.getProductID() )
                .name( product.getName() )
                .price( product.getPrice() )
                .description( product.getDescription() )
                .storeId( product.getStoreId() )
                .imageUrl( product.getImageUrl() )
                .build();
    }

    public ProductResponseDTO DomainToResponseDTO( Product product, String currency ) {
        return ProductResponseDTO.builder()
                .productID( product.getProductID() )
                .name( product.getName() )
                .price( new ProductResponseDTO.PriceResponse( currency, product.getPrice() ) )
                .description( product.getDescription() )
                .store( product.getStoreId() )
                .imageURL( product.getImageUrl() )
                .build();
    }

    public Product requestToDomain(ProductRequestDTO productRequestDTO) {
        return Product.builder()
                .productID( UUID.randomUUID().toString() )
                .name( productRequestDTO.name() )
                .price( productRequestDTO.price() )
                .description( productRequestDTO.description() )
                .build();
    }
}
