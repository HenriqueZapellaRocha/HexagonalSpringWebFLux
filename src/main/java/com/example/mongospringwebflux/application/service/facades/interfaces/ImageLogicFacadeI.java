package com.example.mongospringwebflux.application.service.facades.interfaces;


import com.example.mongospringwebflux.domain.product.Product;
import com.example.mongospringwebflux.domain.user.User;
import org.springframework.http.codec.multipart.FilePart;
import reactor.core.publisher.Mono;

public interface ImageLogicFacadeI {

    public Mono<String> validateAndPersistsImage(FilePart image, String productId, User currentUser );

    public Mono<Void> deleteImage( Product product );
}
