package com.example.mongospringwebflux.application.service.services;


import com.example.mongospringwebflux.application.service.interfaces.AdminServiceI;
import com.example.mongospringwebflux.domain.product.Product;
import com.example.mongospringwebflux.domain.product.ProductRepositoryI;
import com.example.mongospringwebflux.domain.store.StoreRepositoryI;
import com.example.mongospringwebflux.domain.user.UserRepositoryI;
import com.example.mongospringwebflux.infrastructure.exception.GlobalException;
import com.example.mongospringwebflux.infrastructure.exception.NotFoundException;
import com.example.mongospringwebflux.adapters.outbound.integration.exchange.ExchangeIntegration;
import com.example.mongospringwebflux.application.service.facades.ImageLogicFacade;
import com.example.mongospringwebflux.domain.DTOS.requests.ProductRequestDTO;
import com.example.mongospringwebflux.domain.DTOS.responses.ProductResponseDTO;
import com.example.mongospringwebflux.domain.DTOS.responses.UserResponseDTO;
import com.example.mongospringwebflux.utils.mappers.ProductMappers;
import com.example.mongospringwebflux.utils.mappers.UserMappers;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.math.BigDecimal;
import java.util.List;

@Service
@AllArgsConstructor
public class AdminService implements AdminServiceI {

    private final ProductRepositoryI productRepository;
    private final UserRepositoryI userRepository;
    private final StoreRepositoryI storeRepository;
    private final ExchangeIntegration exchangeIntegration;
    private final ImageLogicFacade imageLogicFacade;
    private final ProductMappers productMappers;
    private final UserMappers userMappers;

    public Mono<ProductResponseDTO> addProductToRelatedStore( ProductRequestDTO productRequest, String from,
                                                              String to, String storeId ) {

        Product product = productMappers.requestToDomain( productRequest );

        return storeRepository.findById( storeId )
                .switchIfEmpty( Mono.defer( () -> Mono.error( new NotFoundException( "The store don't exist" ) ) ) )
                .flatMap( storeEntity -> exchangeIntegration.makeExchange( from,to )
                        .flatMap( exchangeRate -> {
                            product.setPrice(product.getPrice()
                                    .multiply( new BigDecimal( String.valueOf( exchangeRate ) ) ));
                            product.setStoreId( storeEntity.getId() );
                            return productRepository.save( product );
                        })
                        .map( savedProduct -> productMappers.DomainToResponseDTO( savedProduct, to ) ));
    }

    public Mono<Void> deleteUserAndAllInformationRelated( String userId ) {

        return userRepository.findById( userId )
                .switchIfEmpty( Mono.defer( () -> Mono.error(new NotFoundException( "User not found" ) )))
                .flatMap( user -> {
                    String storeId = user.getStoreId();

                    return productRepository.findAllByStoreId( storeId )
                            .flatMap( imageLogicFacade::deleteImage )
                            .then(
                                    Mono.when(
                                            userRepository.delete( user ),
                                            storeRepository.deleteById( storeId ),
                                            productRepository.deleteAllByStoreId( storeId )
                                    ).onErrorResume( e -> Mono.error( new GlobalException( "Error deleting user" )) )
                            );
                });
    }

    public Mono<Void> deleteManyProducts( List<String> productIds ) {
        return productRepository.findAllById( productIds )
                .flatMap( product ->
                        Mono.when(
                        imageLogicFacade.deleteImage( product ),
                        productRepository.deleteById( product.getProductID() )

                )).then();
    }

    public Flux<UserResponseDTO> getAllUsers() {
        return userRepository.findAll()
                .map( userMappers::DomainToResponseDTO );
    }
}
