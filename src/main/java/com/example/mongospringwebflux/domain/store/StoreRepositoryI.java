package com.example.mongospringwebflux.domain.store;

import com.example.mongospringwebflux.domain.user.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface StoreRepositoryI {

    Mono<Store> findById(String id );
    Mono<Store> save( Store store );
    Flux<Store> findAll();
}
