package com.example.mappers;

import com.example.domain.DTOS.requests.ProductRequestDTO;
import com.example.domain.DTOS.responses.UserResponseDTO;
import com.example.domain.user.User;
import com.example.outbound.repository.entities.ProductEntity;
import com.example.outbound.repository.entities.UserEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserMappers {

    public User EntityToDomain( UserEntity user ) {

        return User.builder()
                .id( user.getId() )
                .login( user.getLogin() )
                .password( user.getPassword() )
                .role( user.getRole() )
                .storeId( user.getStoreId() )
                .build();
    }

    public UserEntity DomainToEntity( User user ) {

        return UserEntity.builder()
                .id( user.getId() )
                .login( user.getLogin() )
                .password( user.getPassword() )
                .role( user.getRole() )
                .storeId( user.getStoreId() )
                .build();
    }

    public UserResponseDTO DomainToResponseDTO(User user ) {
        return UserResponseDTO.builder()
                .id( user.getId() )
                .login( user.getLogin() )
                .role( user.getRole() )
                .storeRelated( user.getStoreId() )
                .build();
    }

    public ProductEntity requestToEntity(ProductRequestDTO productRequestDTO ) {
        return ProductEntity.builder()
                .productID( UUID.randomUUID().toString() )
                .name( productRequestDTO.name() )
                .price( productRequestDTO.price() )
                .description( productRequestDTO.description() )
                .build();
    }
}
