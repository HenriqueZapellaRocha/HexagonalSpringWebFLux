package com.example.outbound.repository.product;


import com.example.outbound.entities.ProductEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface JpaProductRepository extends ReactiveMongoRepository<ProductEntity, String> {

    Flux<ProductEntity> getByStoreId(String storeId );
    Mono<Void> deleteAllByStoreId( String storeId );
    Flux<ProductEntity> findAllByStoreId( String storeId );

}
