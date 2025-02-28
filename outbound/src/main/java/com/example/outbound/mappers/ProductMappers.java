package com.example.outbound.mappers;




import com.example.domain.product.Product;
import com.example.entities.ProductEntity;
import org.springframework.stereotype.Service;


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

}
