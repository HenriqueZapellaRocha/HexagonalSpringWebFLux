package com.example.mongospringwebflux.adapters.outbound.repository.product;

import com.example.mongospringwebflux.domain.product.Product;
import com.example.mongospringwebflux.domain.product.ProductRepositoryI;
import com.example.mongospringwebflux.utils.mappers.ProductMappers;
import lombok.Data;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.List;


@Data
@Repository
public class ProductRepositoryImp implements ProductRepositoryI {

    private final JpaProductRepository jpaProductRepository;
    private final ProductMappers productMappers;

    @Override
    public Mono<Product> findById( String id ) {
        return jpaProductRepository.findById( id )
                .map( productMappers::EntityToDomain );
    }

    @Override
    public Mono<Product> save( Product product ) {
        return jpaProductRepository.save( productMappers.DomainToEntity( product ) )
                .map( productMappers::EntityToDomain );
    }

    @Override
    public Flux<Product> findAll() {
        return jpaProductRepository.findAll()
                .map( productMappers::EntityToDomain );
    }

    @Override
    public Flux<Product> getByStoreId( String storeId ) {
        return jpaProductRepository.getByStoreId( storeId )
                .map( productMappers::EntityToDomain );
    }

    @Override
    public Mono<Void> deleteAllByStoreId( String storeId ) {
        return jpaProductRepository.deleteAllByStoreId( storeId );
    }

    @Override
    public Flux<Product> findAllByStoreId( String storeId ) {
        return jpaProductRepository.findAllByStoreId( storeId )
                .map( productMappers::EntityToDomain );
    }

    @Override
    public Flux<Product> findAllById(List<String> ids) {
        return jpaProductRepository.findAllById( ids )
                .map( productMappers::EntityToDomain );
    }

    @Override
    public Mono<Void> delete(Product product) {
        return jpaProductRepository.delete( productMappers.DomainToEntity( product ) );
    }
}
