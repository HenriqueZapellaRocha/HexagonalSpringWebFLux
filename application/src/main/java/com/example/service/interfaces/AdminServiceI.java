package com.example.service.interfaces;

import com.example.domain.DTOS.requests.ProductRequestDTO;
import com.example.domain.DTOS.responses.ProductResponseDTO;
import com.example.domain.DTOS.responses.UserResponseDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.List;

public interface AdminServiceI {

    public Mono<ProductResponseDTO> addProductToRelatedStore(ProductRequestDTO product, String from,
                                                             String to, String storeId );

    public Mono<Void> deleteUserAndAllInformationRelated( String userId );

    public Mono<Void> deleteManyProducts( List<String> productIds );

    public Flux<UserResponseDTO> getAllUsers();
}
