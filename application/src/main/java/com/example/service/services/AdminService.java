package com.example.service.services;


import com.example.domain.DTOS.requests.ProductRequestDTO;
import com.example.domain.DTOS.responses.ProductResponseDTO;
import com.example.domain.DTOS.responses.UserResponseDTO;
import com.example.domain.product.Product;
import com.example.domain.product.ProductRepositoryI;
import com.example.domain.store.StoreRepositoryI;
import com.example.domain.user.UserRepositoryI;
import com.example.outbound.integration.exchange.ExchangeIntegration;
import com.example.service.interfaces.AdminServiceI;
import com.example.domain.DTOS.exceptions.GlobalException;
import com.example.domain.DTOS.exceptions.NotFoundException;
import com.example.service.facades.ImageLogicFacade;
import com.example.service.mappers.ServiceProductMappers;
import com.example.service.mappers.ServiceUserMappers;
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
    private final ServiceProductMappers productMappers;
    private final ServiceUserMappers userMappers;

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
