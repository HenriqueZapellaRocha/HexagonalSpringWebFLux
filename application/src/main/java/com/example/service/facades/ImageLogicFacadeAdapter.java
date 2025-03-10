package com.example.service.facades;


import com.example.domain.product.Product;
import com.example.domain.product.ProductRepositoryI;
import com.example.domain.user.User;
import com.example.outbound.minio.MinioAdapter;
import com.example.service.facades.interfaces.ImageLogicFacadePort;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.commons.compress.utils.FileNameUtils;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Data
@AllArgsConstructor
@Service
public class ImageLogicFacadeAdapter implements ImageLogicFacadePort {

    private final ProductRepositoryI productRepository;
    private final MinioAdapter minioAdapter;

    public Mono<String> validateAndPersistsImage( FilePart image, String productId, User currentUser ) {

        String fileName = productId + '.' + FileNameUtils.getExtension( image.filename() );

        return productRepository.getByStoreId( currentUser.getStoreId() )
                .filter( product -> product.getProductID().equals( productId ) )
                .switchIfEmpty( Mono.defer( () -> Mono.error(
                        new BadCredentialsException( "This product don't exist or not related with this store" ) ) ))
                .flatMap( product -> {
                    product.setImageUrl( "http://localhost:9000/product-images/"+product.getProductID() );
                    return productRepository.save( product );
                } )
                .flatMap( product -> minioAdapter.uploadFile( Mono.just( image ), fileName ) )
                .then( Mono.just( "http://localhost:9000/product-images/"+fileName ) );
    }

    public Mono<Void> deleteImage( Product product ) {

        if( !product.getImageUrl().equals( "has no image" ) )
            return minioAdapter.deleteFile( product.getProductID() );

        return Mono.empty();
    }
}
