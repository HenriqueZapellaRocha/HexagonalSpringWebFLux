package com.example.service.facades.interfaces;


import com.example.domain.product.Product;
import com.example.domain.user.User;
import org.springframework.http.codec.multipart.FilePart;
import reactor.core.publisher.Mono;

public interface ImageLogicFacadePort {

    public Mono<String> validateAndPersistsImage(FilePart image, String productId, User currentUser );

    public Mono<Void> deleteImage( Product product );
}
