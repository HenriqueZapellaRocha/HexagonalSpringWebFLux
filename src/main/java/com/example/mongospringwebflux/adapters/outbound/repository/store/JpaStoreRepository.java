package com.example.mongospringwebflux.adapters.outbound.repository.store;

import com.example.mongospringwebflux.adapters.outbound.repository.entities.StoreEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;


public interface JpaStoreRepository extends ReactiveMongoRepository<StoreEntity, String> { }
