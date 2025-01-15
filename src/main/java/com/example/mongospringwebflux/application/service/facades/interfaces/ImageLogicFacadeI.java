package com.example.mongospringwebflux.application.service.facades.interfaces;

import com.example.mongospringwebflux.adapters.outbound.repository.entities.ProductEntity;
import com.example.mongospringwebflux.adapters.outbound.repository.entities.UserEntity;
import org.springframework.http.codec.multipart.FilePart;
import reactor.core.publisher.Mono;

public interface ImageLogicFacadeI {

    public Mono<String> validateAndPersistsImage(FilePart image, String productId, UserEntity currentUser );

    public Mono<Void> deleteImage( ProductEntity product );
}
