package com.example.mongospringwebflux.application.service.services;



import java.math.BigDecimal;
import java.util.List;

import com.example.mongospringwebflux.application.service.interfaces.ProductServiceI;
import com.example.mongospringwebflux.infrastructure.exception.NotFoundException;
import com.example.mongospringwebflux.adapters.outbound.integration.exchange.ExchangeIntegration;
import com.example.mongospringwebflux.adapters.outbound.repository.product.JpaProductRepository;
import com.example.mongospringwebflux.adapters.outbound.repository.store.JpaStoreRepository;
import com.example.mongospringwebflux.adapters.outbound.repository.entities.ProductEntity;
import com.example.mongospringwebflux.adapters.outbound.repository.entities.UserEntity;
import com.example.mongospringwebflux.application.service.facades.ImageLogicFacade;
import com.example.mongospringwebflux.domain.DTOS.requests.ProductRequestDTO;
import com.example.mongospringwebflux.domain.DTOS.responses.ProductResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RequiredArgsConstructor
@Service
public class ProductService implements ProductServiceI {

    private final JpaProductRepository jpaProductRepository;
    private final ExchangeIntegration exchangeIntegration;
    private final JpaStoreRepository jpaStoreRepository;
    private final ImageLogicFacade imageLogicFacade;

    public Mono<ProductResponseDTO> add( ProductRequestDTO product, String from, String to, UserEntity currentUser ) {
        ProductEntity productEntity = product.toEntity();

        return jpaStoreRepository.findById( currentUser.getStoreId() )
                .zipWith( exchangeIntegration.makeExchange( from,to ) )
                .flatMap( tuple -> {
                    productEntity.setPrice( product.price()
                                    .multiply( new BigDecimal(String.valueOf( tuple.getT2() ) ) ));
                    productEntity.setStoreId( tuple.getT1().getId() );
                    return jpaProductRepository.save( productEntity );
                } ).map( savedProductEntity -> ProductResponseDTO.entityToResponse( savedProductEntity, to ) );
    }

    public Mono<ProductResponseDTO> getById( String id, String from, String to ) {
        return jpaProductRepository.findById( id )
                        .switchIfEmpty( Mono.defer( () -> Mono.error( new NotFoundException( "No product found" ) ) ))
                .zipWith( exchangeIntegration.makeExchange( from,to ), ( product,exchangeRate ) -> {
                    product.setPrice( product.getPrice()
                            .multiply( new BigDecimal( String.valueOf( exchangeRate ) ) ) );
                    return ProductResponseDTO.entityToResponse( product, to );
                });
    }

    public Mono<ProductResponseDTO> update( ProductRequestDTO product, String id, String storeId,
                                            String from, String to ) {

        ProductEntity productEntity = product.toEntity( id );

        return jpaProductRepository.findById( id )
                .switchIfEmpty(Mono.defer( () ->  Mono.error ( new NotFoundException( "No product found" ) ) ) )
                .filter( product1 -> product1.getStoreId().equals( storeId ) )
                .zipWith( exchangeIntegration.makeExchange( from,to ) )
                .flatMap( tuple -> {
                        tuple.getT1().setDescription( productEntity.getDescription() );
                        tuple.getT1().setName( productEntity.getName() );
                        tuple.getT1().setPrice( productEntity.getPrice() );

                        tuple.getT1().setPrice( productEntity.getPrice()
                            .multiply( new BigDecimal(String.valueOf( tuple.getT2()) ) ));
                    return jpaProductRepository.save( tuple.getT1() );

                 }).map( productSaved -> ProductResponseDTO.entityToResponse( productSaved, "USD" ) );
        }

    public Flux<ProductResponseDTO> getAll( String from, String to ) {
        return exchangeIntegration.makeExchange( from, to )
                .flatMapMany( exchangeRate -> jpaProductRepository.findAll()
                        .map( productEntity -> {
                            productEntity.setPrice( productEntity.getPrice()
                                    .multiply( new BigDecimal( String.valueOf( exchangeRate ))));

                            return ProductResponseDTO.entityToResponse( productEntity, to );
                        }));
    }


    public Mono<Void> deleteMany( List<String> ids, String storeId ) {

        return jpaProductRepository.findAllById( ids )
                .filter( productEntities -> productEntities.getStoreId().equals( storeId ) )
                .switchIfEmpty( Mono.defer( () -> Mono.error( new NotFoundException( "No product found" ) ) ) )
                .flatMap( product -> jpaProductRepository.delete( product )
                        .then( imageLogicFacade.deleteImage( product ) ))
                .then();
    }


    public Flux<ProductResponseDTO> getAllProductsRelatedStore( String storeId, String currency ) {

        return jpaProductRepository.getByStoreId( storeId )
                .switchIfEmpty( Mono.defer( () -> Mono.error( new NotFoundException( "No product found" ) ) ) )
                .map( productEntity -> ProductResponseDTO.entityToResponse( productEntity, currency ) );

    }
}

