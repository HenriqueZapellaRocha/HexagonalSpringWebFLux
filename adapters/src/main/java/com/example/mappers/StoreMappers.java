package com.example.mappers;

import com.example.domain.DTOS.responses.StoreResponseDTO;
import com.example.domain.store.Store;
import com.example.outbound.repository.entities.StoreEntity;
import org.springframework.stereotype.Service;

@Service
public class StoreMappers {

    public Store EntityToDomain( StoreEntity storeEntity) {

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

    public StoreResponseDTO DomainToResponseDTO(Store store ) {
        return StoreResponseDTO.builder()
                .id( store.getId() )
                .name( store.getName() )
                .city( store.getCity() )
                .state( store.getState() )
                .build();
    }
}
