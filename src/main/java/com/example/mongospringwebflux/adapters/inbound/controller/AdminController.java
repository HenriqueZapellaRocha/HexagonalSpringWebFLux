package com.example.mongospringwebflux.adapters.inbound.controller;


import com.example.domain.DTOS.requests.ProductRequestDTO;
import com.example.domain.DTOS.responses.ProductResponseDTO;
import com.example.domain.DTOS.responses.UserResponseDTO;
import com.example.service.services.AdminService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping( "/admin" )
public class AdminController {

    private final AdminService adminService;

    @DeleteMapping("/user/{id}")
    public Mono<Void> deleteUser( @PathVariable String id ) {
        return adminService.deleteUserAndAllInformationRelated( id );
    }

    @PostMapping( "/product/{storeId}" )
    public Mono<ProductResponseDTO> addProduct(@PathVariable String storeId,
                                               @RequestParam( name = "currency" ) String currency,
                                               @RequestBody @Valid ProductRequestDTO productRequestDTO ) {

        return adminService.addProductToRelatedStore( productRequestDTO, currency, "USD", storeId  );
    }

    @DeleteMapping( "/product" )
    public Mono<Void> deleteProduct( @RequestBody List<String> ids ) {
        return adminService.deleteManyProducts( ids );
    }

    @GetMapping( "/users" )
    public Flux<UserResponseDTO> getAllUsers() {
        return adminService.getAllUsers();
    }


}
