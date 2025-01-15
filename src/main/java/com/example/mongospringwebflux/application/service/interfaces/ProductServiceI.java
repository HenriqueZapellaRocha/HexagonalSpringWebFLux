package com.example.mongospringwebflux.application.service.interfaces;

import com.example.mongospringwebflux.domain.DTOS.requests.ProductRequestDTO;
import com.example.mongospringwebflux.domain.DTOS.responses.ProductResponseDTO;
import com.example.mongospringwebflux.adapters.outbound.repository.entities.UserEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.List;

public interface ProductServiceI {

    public Mono<ProductResponseDTO> add(ProductRequestDTO product, String from, String to, UserEntity currentUser );

    public Mono<ProductResponseDTO> getById( String id, String from, String to );

    public Mono<ProductResponseDTO> update( ProductRequestDTO product, String id, String storeId,
                                            String from, String to );

    public Flux<ProductResponseDTO> getAll(String from, String to );


    public Mono<Void> deleteMany(List<String> ids, String storeId );

    public Flux<ProductResponseDTO> getAllProductsRelatedStore( String storeId, String currency );
}
