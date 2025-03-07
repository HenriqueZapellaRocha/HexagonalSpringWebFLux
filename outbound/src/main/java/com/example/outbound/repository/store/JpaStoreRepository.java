package com.example.outbound.repository.store;

import com.example.outbound.entities.StoreEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaStoreRepository extends ReactiveMongoRepository<StoreEntity, String> { }
