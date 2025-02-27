package com.example.service.interfaces;


import com.example.domain.DTOS.requests.ProductRequestDTO;
import com.example.domain.DTOS.responses.ProductResponseDTO;
import com.example.domain.user.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.List;

public interface ProductServiceI {

    public Mono<ProductResponseDTO> add( ProductRequestDTO product, String from, String to, User currentUser );

    public Mono<ProductResponseDTO> getById( String id, String from, String to );

    public Mono<ProductResponseDTO> update( ProductRequestDTO product, String id, String storeId,
                                            String from, String to );

    public Flux<ProductResponseDTO> getAll( String from, String to );


    public Mono<Void> deleteMany( List<String> ids, String storeId );

    public Flux<ProductResponseDTO> getAllProductsRelatedStore( String storeId, String currency );
}
