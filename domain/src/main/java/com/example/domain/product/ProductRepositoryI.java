package com.example.domain.product;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.List;


public interface ProductRepositoryI {

    Mono<Product> findById( String id );
    Mono<Product> save( Product product );
    Flux<Product> findAll();
    Flux<Product> getByStoreId( String storeId );
    Mono<Void> deleteAllByStoreId( String storeId );
    Flux<Product> findAllByStoreId( String storeId );
    Flux<Product> findAllById( List<String> ids );
    Mono<Void> delete( Product product );
    Mono<Void> deleteById( String id );
}
