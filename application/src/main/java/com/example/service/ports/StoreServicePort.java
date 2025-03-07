package com.example.service.ports;


import com.example.domain.DTOS.requests.StoreCreationRequestDTO;
import com.example.domain.DTOS.responses.StoreResponseDTO;
import com.example.domain.store.Store;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface StoreServicePort {

    public Mono<Store> createStore(StoreCreationRequestDTO storeRequest, String id );

    public Flux<StoreResponseDTO> getAllStores();

    public Mono<StoreResponseDTO> getStoreById( String id );
}
