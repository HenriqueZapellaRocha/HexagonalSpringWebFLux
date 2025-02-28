package com.example.service.mappers;



import com.example.domain.DTOS.responses.UserResponseDTO;
import com.example.domain.user.User;
import com.example.entities.UserEntity;
import org.springframework.stereotype.Service;


@Service
public class ServiceUserMappers {

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
