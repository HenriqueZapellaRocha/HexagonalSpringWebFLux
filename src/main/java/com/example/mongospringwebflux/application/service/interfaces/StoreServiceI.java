package com.example.mongospringwebflux.application.service.interfaces;

import com.example.mongospringwebflux.domain.DTOS.requests.StoreCreationRequestDTO;
import com.example.mongospringwebflux.domain.DTOS.responses.StoreResponseDTO;
import com.example.mongospringwebflux.adapters.outbound.repository.entities.StoreEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface StoreServiceI {

    public Mono<StoreEntity> createStore(StoreCreationRequestDTO storeRequest, String id );


    public Flux<StoreResponseDTO> getAllStores();

    public Mono<StoreResponseDTO> getStoreById( String id );
}
