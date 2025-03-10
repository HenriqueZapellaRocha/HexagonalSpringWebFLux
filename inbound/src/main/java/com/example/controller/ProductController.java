package com.example.controller;


import com.example.domain.DTOS.requests.ProductRequestDTO;
import com.example.domain.DTOS.responses.ProductResponseDTO;
import com.example.domain.user.User;
import com.example.domain.DTOS.exceptions.GlobalException;
import com.example.imageValidations.interfaces.ValidFile;
import com.example.service.facades.interfaces.ImageLogicFacadePort;
import com.example.service.ports.CookieServicePort;
import com.example.service.ports.ProductServicePort;
import jakarta.validation.Valid;
import org.apache.commons.compress.utils.FileNameUtils;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.List;



@RequiredArgsConstructor
@RestController
@RequestMapping( "/product" )
public class ProductController {

    private final ProductServicePort productService;
    private final ImageLogicFacadePort imageLogicFacade;
    private final CookieServicePort cookieService;


    @PostMapping( "/add" )
    public Mono<ProductResponseDTO> add( @RequestBody @Valid ProductRequestDTO product,
                                         @RequestParam( name = "currency" ) String currency,
                                         @AuthenticationPrincipal User currentUser ) {

        return productService.add( product,currency, "USD", currentUser );
    }

    @PostMapping( "/uploadProductImage" )
    public Mono<String> uploadFile( @RequestPart("files") @ValidFile FilePart filePart,
                                    @RequestPart("productId") String productId,
                                    @AuthenticationPrincipal User currentUser ) {

        return imageLogicFacade.validateAndPersistsImage( filePart, productId, currentUser );
    }


    @GetMapping( "/{id}" )
    public Mono<ProductResponseDTO> getById( @PathVariable String id,
                                             @RequestParam( name = "currency" ) String currency,
                                             ServerHttpResponse response ) {

        return productService.getById( id, "USD", currency )
                .doOnNext( product ->
                    cookieService.setCookie( response, product.productID() )
                );
    }

    @GetMapping( "/last" )
    public Mono<ProductResponseDTO> getLast( @CookieValue("last") String cookie,
                                             @RequestParam( name = "currency" ) String currency ) {
        return productService.getById ( cookie, "USD", currency );
    }

    @GetMapping( "/All" )
    public Flux<ProductResponseDTO> getAll( @RequestParam( name = "currency" ) String currency ) {
        return productService.getAll( "USD", currency );
    }

    @GetMapping( "/storeRelated/{id}" )
    public Flux<ProductResponseDTO> allProductStoreRelated( @RequestParam( name = "currency" ) String currency,
                                                            @PathVariable String id ) {
        return productService.getAllProductsRelatedStore( id, currency );
    }

    @PutMapping( "/{id}" )
    public Mono<ProductResponseDTO> update( @RequestBody @Valid ProductRequestDTO product,
                                            @PathVariable String id,
                                            @AuthenticationPrincipal User currentUser,
                                            @RequestParam( name = "currency" ) String currency ) {

        return productService.update( product, id, currentUser.getStoreId(), currency, "USD" );
    }

    @DeleteMapping( "/{id}" )
    public Mono<Void> deleteById( @PathVariable String id,
                                  @AuthenticationPrincipal User currentUser ) {
        return productService.deleteMany( List.of(id), currentUser.getStoreId() );
    }

    @DeleteMapping
    public Mono<Void> deleteMany( @RequestBody List<String> id,
                                  @AuthenticationPrincipal User currentUser ) {
        return productService.deleteMany( id, currentUser.getStoreId() );
    }
}
