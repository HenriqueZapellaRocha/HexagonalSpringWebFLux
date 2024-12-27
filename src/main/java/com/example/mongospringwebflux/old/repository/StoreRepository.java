package com.example.mongospringwebflux.repository;

import com.example.mongospringwebflux.repository.entity.StoreEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;


public interface StoreRepository extends ReactiveMongoRepository<StoreEntity, String> { }
