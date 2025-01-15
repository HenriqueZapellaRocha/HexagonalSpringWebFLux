package com.example.mongospringwebflux.utils.mappers;

import com.example.mongospringwebflux.adapters.outbound.repository.entities.UserEntity;
import com.example.mongospringwebflux.domain.DTOS.responses.UserResponseDTO;
import com.example.mongospringwebflux.domain.user.User;
import org.springframework.stereotype.Service;

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

    public UserResponseDTO DomainToResponseDTO( User user ) {
        return UserResponseDTO.builder()
                .id( user.getId() )
                .login( user.getLogin() )
                .role( user.getRole() )
                .storeRelated( user.getStoreId() )
                .build();
    }
}
