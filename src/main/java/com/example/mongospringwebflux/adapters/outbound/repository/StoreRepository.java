package com.example.mongospringwebflux.adapters.outbound.repository;

import com.example.mongospringwebflux.adapters.outbound.repository.entity.StoreEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;


public interface StoreRepository extends ReactiveMongoRepository<StoreEntity, String> { }
