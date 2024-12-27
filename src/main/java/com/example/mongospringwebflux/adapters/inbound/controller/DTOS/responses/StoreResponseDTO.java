package com.example.mongospringwebflux.adapters.inbound.controller.DTOS.responses;

import com.example.mongospringwebflux.adapters.outbound.repository.entity.StoreEntity;
import lombok.Builder;

@Builder
public record StoreResponseDTO(

        String id,
        String name,
        String city,
        String state

) {
    public static StoreResponseDTO entityToResponse( StoreEntity storeEntity ) {
        return StoreResponseDTO.builder()
                .id( storeEntity.getId() )
                .name( storeEntity.getName() )
                .city( storeEntity.getCity() )
                .state( storeEntity.getState() )
                .build();
    }
}
