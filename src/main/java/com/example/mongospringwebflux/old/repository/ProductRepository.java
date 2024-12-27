package com.example.mongospringwebflux.repository;


import com.example.mongospringwebflux.repository.entity.ProductEntity;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface ProductRepository extends ReactiveMongoRepository<ProductEntity, String> {

    Flux<ProductEntity> getByStoreId( String storeId );
    Mono<Void> deleteAllByStoreId( String storeId );
    Flux<ProductEntity> findAllByStoreId( String storeId );

}
