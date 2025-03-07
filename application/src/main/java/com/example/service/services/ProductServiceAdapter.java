package com.example.service.services;



import java.math.BigDecimal;
import java.util.List;

import com.example.domain.DTOS.requests.ProductRequestDTO;
import com.example.domain.DTOS.responses.ProductResponseDTO;
import com.example.domain.product.Product;
import com.example.domain.product.ProductRepositoryI;
import com.example.domain.store.StoreRepositoryI;
import com.example.domain.user.User;
import com.example.outbound.entities.ProductEntity;
import com.example.outbound.integration.exchange.ExchangeIntegration;
import com.example.service.facades.interfaces.ImageLogicFacadePort;
import com.example.service.ports.ProductServicePort;
import com.example.domain.DTOS.exceptions.NotFoundException;
import com.example.service.mappers.ServiceProductMappers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RequiredArgsConstructor
@Service
public class ProductServiceAdapter implements ProductServicePort {

    private final ProductRepositoryI productRepository;
    private final ExchangeIntegration exchangeIntegration;
    private final StoreRepositoryI storeRepository;
    private final ImageLogicFacadePort imageLogicFacade;
    private final ServiceProductMappers productMappers;

    public Mono<ProductResponseDTO> add( ProductRequestDTO product, String from, String to, User currentUser ) {
        Product productDomain = productMappers.requestToDomain( product );

        return storeRepository.findById( currentUser.getStoreId() )
                .zipWith( exchangeIntegration.makeExchange( from,to ) )
                .flatMap( tuple -> {
                    productDomain.setPrice( product.price()
                                    .multiply( new BigDecimal(String.valueOf( tuple.getT2() ) ) ));
                    productDomain.setStoreId( tuple.getT1().getId() );
                    return productRepository.save( productDomain );
                } ).map( savedProduct -> productMappers.DomainToResponseDTO( savedProduct, to ) );
    }

    public Mono<ProductResponseDTO> getById( String id, String from, String to ) {
        return productRepository.findById( id )
                        .switchIfEmpty( Mono.defer( () -> Mono.error( new NotFoundException( "No product found" ) ) ))
                .zipWith( exchangeIntegration.makeExchange( from,to ), ( product,exchangeRate ) -> {
                    product.setPrice( product.getPrice()
                            .multiply( new BigDecimal( String.valueOf( exchangeRate ) ) ) );
                    return productMappers.DomainToResponseDTO( product, to );
                });
    }

    public Mono<ProductResponseDTO> update( ProductRequestDTO product, String id, String storeId,
                                            String from, String to ) {

        ProductEntity productEntity = productMappers.requestToEntity( product );

        return productRepository.findById( id )
                .switchIfEmpty(Mono.defer( () ->  Mono.error ( new NotFoundException( "No product found" ) ) ) )
                .filter( product1 -> product1.getStoreId().equals( storeId ) )
                .zipWith( exchangeIntegration.makeExchange( from,to ) )
                .flatMap( tuple -> {
                        tuple.getT1().setDescription( productEntity.getDescription() );
                        tuple.getT1().setName( productEntity.getName() );
                        tuple.getT1().setPrice( productEntity.getPrice() );

                        tuple.getT1().setPrice( productEntity.getPrice()
                            .multiply( new BigDecimal(String.valueOf( tuple.getT2()) ) ));
                    return productRepository.save( tuple.getT1() );

                 }).map( productSaved -> productMappers.DomainToResponseDTO( productSaved, "USD" ) );
        }

    public Flux<ProductResponseDTO> getAll( String from, String to ) {
        return exchangeIntegration.makeExchange( from, to )
                .flatMapMany( exchangeRate -> productRepository.findAll()
                        .map( product -> {
                            product.setPrice( product.getPrice()
                                    .multiply( new BigDecimal( String.valueOf( exchangeRate ))));

                            return productMappers.DomainToResponseDTO( product, to );
                        }));
    }


    public Mono<Void> deleteMany( List<String> ids, String storeId ) {

        return productRepository.findAllById( ids )
                .filter( productEntities -> productEntities.getStoreId().equals( storeId ) )
                .switchIfEmpty( Mono.defer( () -> Mono.error( new NotFoundException( "No product found" ) ) ) )
                .flatMap( product -> productRepository.delete( product )
                        .then( imageLogicFacade.deleteImage( product ) ))
                .then();
    }


    public Flux<ProductResponseDTO> getAllProductsRelatedStore( String storeId, String currency ) {

        return productRepository.getByStoreId( storeId )
                .switchIfEmpty( Mono.defer( () -> Mono.error( new NotFoundException( "No product found" ) ) ) )
                .map( productEntity -> productMappers.DomainToResponseDTO( productEntity, currency ) );

    }
}

