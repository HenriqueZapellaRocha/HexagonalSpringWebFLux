package com.example.mongospringwebflux.application.service.services;


import com.example.mongospringwebflux.application.service.interfaces.AdminServiceI;
import com.example.mongospringwebflux.infrastructure.exception.GlobalException;
import com.example.mongospringwebflux.infrastructure.exception.NotFoundException;
import com.example.mongospringwebflux.adapters.outbound.integration.exchange.ExchangeIntegration;
import com.example.mongospringwebflux.adapters.outbound.repository.product.JpaProductRepository;
import com.example.mongospringwebflux.adapters.outbound.repository.store.JpaStoreRepository;
import com.example.mongospringwebflux.adapters.outbound.repository.user.JpaUserRepository;
import com.example.mongospringwebflux.adapters.outbound.repository.entities.ProductEntity;
import com.example.mongospringwebflux.adapters.outbound.repository.entities.UserEntity;
import com.example.mongospringwebflux.application.service.facades.ImageLogicFacade;
import com.example.mongospringwebflux.domain.DTOS.requests.ProductRequestDTO;
import com.example.mongospringwebflux.domain.DTOS.responses.ProductResponseDTO;
import com.example.mongospringwebflux.domain.DTOS.responses.UserResponseDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.math.BigDecimal;
import java.util.List;

@Service
@AllArgsConstructor
public class AdminService implements AdminServiceI {

    private final JpaProductRepository jpaProductRepository;
    private final JpaUserRepository jpaUserRepository;
    private final JpaStoreRepository jpaStoreRepository;
    private final ExchangeIntegration exchangeIntegration;
    private final ImageLogicFacade imageLogicFacade;

    public Mono<ProductResponseDTO> addProductToRelatedStore( ProductRequestDTO product, String from,
                                                              String to, String storeId ) {
        ProductEntity productEntity = product.toEntity();

        return jpaStoreRepository.findById( storeId )
                .switchIfEmpty( Mono.defer( () -> Mono.error( new NotFoundException( "The store don't exist" ) ) ) )
                .flatMap( storeEntity -> exchangeIntegration.makeExchange( from,to )
                        .flatMap( exchangeRate -> {
                            productEntity.setPrice(product.price()
                                    .multiply( new BigDecimal( String.valueOf( exchangeRate ) ) ));
                            productEntity.setStoreId( storeEntity.getId() );
                            return jpaProductRepository.save( productEntity );
                        })
                        .map( savedProductEntity -> ProductResponseDTO.entityToResponse( savedProductEntity, to ) ));
    }

    public Mono<Void> deleteUserAndAllInformationRelated( String userId ) {

        return jpaUserRepository.findById( userId )
                .switchIfEmpty( Mono.defer( () -> Mono.error(new NotFoundException( "User not found" ) )))
                .flatMap( userEntity -> {
                    String storeId = userEntity.getStoreId();

                    return jpaProductRepository.findAllByStoreId( storeId )
                            .flatMap( imageLogicFacade::deleteImage )
                            .then(
                                    Mono.when(
                                            jpaUserRepository.delete( userEntity ),
                                            jpaStoreRepository.deleteById( userEntity.getStoreId() ),
                                            jpaProductRepository.deleteAllByStoreId( storeId )
                                    ).onErrorResume( e -> Mono.error( new GlobalException( "Error deleting user" )) )
                            );
                });
    }

    public Mono<Void> deleteManyProducts( List<String> productIds ) {
        return jpaProductRepository.findAllById( productIds )
                .flatMap(product ->
                        Mono.when(
                        imageLogicFacade.deleteImage( product ),
                        jpaProductRepository.deleteById( product.getProductID() )

                )).then();
    }

    public Flux<UserResponseDTO> getAllUsers() {
        return jpaUserRepository.findAll()
                .map(UserEntity::entityToResponseDTO);
    }
}
