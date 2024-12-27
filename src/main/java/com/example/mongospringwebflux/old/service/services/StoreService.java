package com.example.mongospringwebflux.service.services;

import com.example.mongospringwebflux.repository.StoreRepository;
import com.example.mongospringwebflux.repository.entity.StoreEntity;
import com.example.mongospringwebflux.v1.controller.DTOS.requests.StoreCreationRequestDTO;
import com.example.mongospringwebflux.v1.controller.DTOS.responses.StoreResponseDTO;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;

    public Mono<StoreEntity> createStore( StoreCreationRequestDTO storeRequest, String id ) {

        return storeRepository.save(
                        StoreEntity.builder()
                                .id( id )
                                .name( storeRequest.name() )
                                .description( storeRequest.description() )
                                .address( storeRequest.address() )
                                .city( storeRequest.city() )
                                .state( storeRequest.state() )
                                .build()
                )
                .onErrorResume( e -> Mono.error( new BadCredentialsException( "This store already exists" ) ) );
    }


    public Flux<StoreResponseDTO> getAllStores() {
        return storeRepository.findAll().map( StoreResponseDTO::entityToResponse );
    }

    public Mono<StoreResponseDTO> getStoreById( String id ) {
        return storeRepository.findById( id )
                .map( StoreResponseDTO::entityToResponse );
    }
}