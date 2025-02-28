package com.example.service.mappers;


import com.example.domain.DTOS.responses.StoreResponseDTO;
import com.example.domain.store.Store;
import org.springframework.stereotype.Service;

@Service
public class ServiceStoreMappers {

    public StoreResponseDTO DomainToResponseDTO(Store store ) {
        return StoreResponseDTO.builder()
                .id( store.getId() )
                .name( store.getName() )
                .city( store.getCity() )
                .state( store.getState() )
                .build();
    }
}
