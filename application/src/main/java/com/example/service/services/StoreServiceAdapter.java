package com.example.service.services;


import com.example.domain.DTOS.requests.StoreCreationRequestDTO;
import com.example.domain.DTOS.responses.StoreResponseDTO;
import com.example.domain.store.Store;
import com.example.domain.store.StoreRepositoryI;
import com.example.service.ports.StoreServicePort;
import com.example.service.mappers.ServiceStoreMappers;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class StoreServiceAdapter implements StoreServicePort {

    private final StoreRepositoryI storeRepository;
    private final ServiceStoreMappers storeMappers;

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