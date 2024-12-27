package com.example.mongospringwebflux.v1.controller;


import com.example.mongospringwebflux.service.services.StoreService;
import com.example.mongospringwebflux.v1.controller.DTOS.responses.StoreResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
@RequestMapping( "/store" )
public class StoreController {


    private final StoreService storeService;

    @GetMapping( "/all" )
    public Flux<StoreResponseDTO> getRelatedStores() {
        return storeService.getAllStores();
    }

    @GetMapping( "/{id}" )
    public Mono<StoreResponseDTO> getStoreById( @PathVariable String id ) {
        return storeService.getStoreById( id );
    }

}
