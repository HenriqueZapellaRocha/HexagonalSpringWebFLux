package com.example.mappers;

import com.example.domain.DTOS.requests.ProductRequestDTO;
import com.example.domain.DTOS.responses.ProductResponseDTO;
import com.example.domain.product.Product;
import com.example.outbound.repository.entities.ProductEntity;
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

    public ProductResponseDTO DomainToResponseDTO(Product product, String currency ) {
        return ProductResponseDTO.builder()
                .productID( product.getProductID() )
                .name( product.getName() )
                .price( new ProductResponseDTO.PriceResponse( currency, product.getPrice() ) )
                .description( product.getDescription() )
                .store( product.getStoreId() )
                .imageURL( product.getImageUrl() )
                .build();
    }



    public ProductEntity requestToEntity( ProductRequestDTO productRequestDTO ) {
        return ProductEntity.builder()
                .productID( UUID.randomUUID().toString() )
                .name( productRequestDTO.name() )
                .price( productRequestDTO.price() )
                .description( productRequestDTO.description() )
                .build();
    }

    public Product requestToDomain( ProductRequestDTO productRequestDTO ) {
        return Product.builder()
                .productID( UUID.randomUUID().toString() )
                .name( productRequestDTO.name() )
                .price( productRequestDTO.price() )
                .description( productRequestDTO.description() )
                .build();
    }
}
