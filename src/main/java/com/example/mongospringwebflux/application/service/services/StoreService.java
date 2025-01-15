package com.example.mongospringwebflux.application.service.services;

import com.example.mongospringwebflux.adapters.outbound.repository.store.JpaStoreRepository;
import com.example.mongospringwebflux.adapters.outbound.repository.entities.StoreEntity;
import com.example.mongospringwebflux.domain.DTOS.requests.StoreCreationRequestDTO;
import com.example.mongospringwebflux.domain.DTOS.responses.StoreResponseDTO;
import com.example.mongospringwebflux.application.service.interfaces.StoreServiceI;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class StoreService implements StoreServiceI {

    private final JpaStoreRepository jpaStoreRepository;

    public Mono<StoreEntity> createStore( StoreCreationRequestDTO storeRequest, String id ) {

        return jpaStoreRepository.save(
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
        return jpaStoreRepository.findAll().map( StoreResponseDTO::entityToResponse );
    }

    public Mono<StoreResponseDTO> getStoreById( String id ) {
        return jpaStoreRepository.findById( id )
                .map( StoreResponseDTO::entityToResponse );
    }
}