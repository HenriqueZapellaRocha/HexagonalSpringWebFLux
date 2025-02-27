package com.example.service.interfaces;


import com.example.domain.DTOS.requests.StoreCreationRequestDTO;
import com.example.domain.DTOS.responses.StoreResponseDTO;
import com.example.domain.store.Store;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface StoreServiceI {

    public Mono<Store> createStore(StoreCreationRequestDTO storeRequest, String id );

    public Flux<StoreResponseDTO> getAllStores();

    public Mono<StoreResponseDTO> getStoreById( String id );
}
