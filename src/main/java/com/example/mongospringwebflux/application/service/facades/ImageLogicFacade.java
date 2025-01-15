package com.example.mongospringwebflux.application.service.facades;


import com.example.mongospringwebflux.adapters.outbound.minio.MinioAdapter;
import com.example.mongospringwebflux.adapters.outbound.repository.product.JpaProductRepository;
import com.example.mongospringwebflux.adapters.outbound.repository.user.JpaUserRepository;
import com.example.mongospringwebflux.adapters.outbound.repository.entities.ProductEntity;
import com.example.mongospringwebflux.adapters.outbound.repository.entities.UserEntity;
import com.example.mongospringwebflux.application.service.facades.interfaces.ImageLogicFacadeI;
import com.example.mongospringwebflux.domain.product.Product;
import com.example.mongospringwebflux.domain.product.ProductRepositoryI;
import com.example.mongospringwebflux.domain.user.User;
import com.example.mongospringwebflux.domain.user.UserRepositoryI;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Data
@AllArgsConstructor
@Service
public class ImageLogicFacade implements ImageLogicFacadeI {

    private final ProductRepositoryI productRepository;
    private final MinioAdapter minioAdapter;

    public Mono<String> validateAndPersistsImage( FilePart image, String productId, User currentUser ) {

        return productRepository.getByStoreId( currentUser.getStoreId() )
                .filter( product -> product.getProductID().equals( productId ) )
                .switchIfEmpty( Mono.defer( () -> Mono.error(
                        new BadCredentialsException( "This product don't exist or not related with this store" ) ) ))
                .flatMap( product -> {
                    product.setImageUrl( "http://localhost:9000/product-images/"+product.getProductID() );
                    return productRepository.save( product );
                } )
                .flatMap( product -> minioAdapter.uploadFile( Mono.just( image ), product.getProductID() ) )
                .then( Mono.just( "http://localhost:9000/product-images/"+productId ) );
    }

    public Mono<Void> deleteImage( Product product ) {

        if( !product.getImageUrl().equals( "has no image" ) )
            return minioAdapter.deleteFile( product.getProductID() );

        return Mono.empty();
    }
}
