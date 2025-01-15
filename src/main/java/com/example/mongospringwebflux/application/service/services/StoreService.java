package com.example.mongospringwebflux.application.service.services;

import com.example.mongospringwebflux.adapters.outbound.repository.store.JpaStoreRepository;
import com.example.mongospringwebflux.adapters.outbound.repository.entities.StoreEntity;
import com.example.mongospringwebflux.domain.DTOS.requests.StoreCreationRequestDTO;
import com.example.mongospringwebflux.domain.DTOS.responses.StoreResponseDTO;
import com.example.mongospringwebflux.application.service.interfaces.StoreServiceI;
import com.example.mongospringwebflux.domain.store.Store;
import com.example.mongospringwebflux.domain.store.StoreRepositoryI;
import com.example.mongospringwebflux.utils.mappers.StoreMappers;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class StoreService implements StoreServiceI {

    private final StoreRepositoryI storeRepository;
    private final StoreMappers storeMappers;

    public Mono<Store> createStore( StoreCreationRequestDTO storeRequest, String id ) {

        return storeRepository.save(
                        Store.builder()
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
        return storeRepository.findAll().map( storeMappers::DomainToResponseDTO );
    }

    public Mono<StoreResponseDTO> getStoreById( String id ) {
        return storeRepository.findById( id )
                .map( storeMappers::DomainToResponseDTO );
    }
}