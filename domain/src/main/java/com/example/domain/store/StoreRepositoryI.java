package com.example.domain.store;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface StoreRepositoryI {

    Mono<Store> findById(String id );
    Mono<Store> save( Store store );
    Flux<Store> findAll();
    Mono<Void> deleteById( String storeId );
}
