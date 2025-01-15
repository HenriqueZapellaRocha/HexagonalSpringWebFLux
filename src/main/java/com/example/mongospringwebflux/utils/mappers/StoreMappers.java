package com.example.mongospringwebflux.utils.mappers;

import com.example.mongospringwebflux.adapters.outbound.repository.entities.StoreEntity;
import com.example.mongospringwebflux.domain.DTOS.responses.StoreResponseDTO;
import com.example.mongospringwebflux.domain.store.Store;
import org.springframework.stereotype.Service;

@Service
public class StoreMappers {

    public Store EntityToDomain(StoreEntity storeEntity) {

        return Store.builder()
                .id( storeEntity.getId() )
                .name( storeEntity.getName() )
                .description( storeEntity.getDescription() )
                .address( storeEntity.getAddress() )
                .city( storeEntity.getCity() )
                .state( storeEntity.getState() )
                .build();
    }

    public StoreEntity DomainToEntity(Store store) {

        return StoreEntity.builder()
                .id( store.getId() )
                .name( store.getName() )
                .description( store.getDescription() )
                .address( store.getAddress() )
                .city( store.getCity() )
                .state( store.getState() )
                .build();
    }

    public StoreResponseDTO DomainToResponseDTO( Store store ) {
        return StoreResponseDTO.builder()
                .id( store.getId() )
                .name( store.getName() )
                .city( store.getCity() )
                .state( store.getState() )
                .build();
    }
}
