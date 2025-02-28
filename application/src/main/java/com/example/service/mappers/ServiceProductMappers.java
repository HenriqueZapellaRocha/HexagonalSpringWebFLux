package com.example.service.mappers;



import com.example.domain.DTOS.requests.ProductRequestDTO;
import com.example.domain.DTOS.responses.ProductResponseDTO;
import com.example.domain.product.Product;
import com.example.entities.ProductEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ServiceProductMappers {

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
