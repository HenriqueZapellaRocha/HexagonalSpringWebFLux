package com.example.mongospringwebflux.integrationTests.controllers;

import com.example.mongospringwebflux.integrationTests.controllers.basicModels.AbstractBaseIntegrationTest;
import com.example.mongospringwebflux.repository.entity.StoreEntity;
import com.example.mongospringwebflux.v1.controller.DTOS.responses.StoreResponseDTO;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;


public class StoreControllerTest extends AbstractBaseIntegrationTest {


    @Test
    public void test_storeController_getAll_returnOkAndAllStores() {

        StoreEntity storeEntity = StoreEntity.builder()
                .name("TEST SOTRE 2")
                .city("TEST CITY")
                .state("TEST STATE")
                .address("TEST ADDRESS")
                .description("TEST DESCRIPTION")
                .build();

        StoreResponseDTO store1 = StoreResponseDTO.entityToResponse( STORE_ENTITY_TEST );
        StoreResponseDTO store2 = StoreResponseDTO.entityToResponse( storeEntity );

        webTestClient.get()
                .uri(uriBuilder -> uriBuilder.path("/store/All")
                        .queryParam("currency", "USD")
                        .build())
                .header("Authorization", "Bearer " + USER_TOKEN )
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList( StoreResponseDTO.class );
    }


    @Test
    public void test_storeController_getAll_notLogged_returnError() {

        StoreEntity storeEntity = StoreEntity.builder()
                .name("TEST SOTRE 2")
                .city("TEST CITY")
                .state("TEST STATE")
                .address("TEST ADDRESS")
                .description("TEST DESCRIPTION")
                .build();

        StoreResponseDTO store1 = StoreResponseDTO.entityToResponse( STORE_ENTITY_TEST );
        StoreResponseDTO store2 = StoreResponseDTO.entityToResponse( storeEntity );

        storeRepository.save(storeEntity).block();

        webTestClient.get()
                .uri(uriBuilder -> uriBuilder.path("/product/All")
                        .queryParam("currency", "USD")
                        .build())
                .header("Authorization", "Bearer " + "123" )
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isUnauthorized();
    }

    @Test
    public void test_storeController_getById_returnOkAndStore() {

        StoreEntity storeEntity = StoreEntity.builder()
                .name("TEST SOTRE 2")
                .city("TEST CITY")
                .state("TEST STATE")
                .address("TEST ADDRESS")
                .description("TEST DESCRIPTION")
                .build();

        webTestClient.get()
                .uri(uriBuilder -> uriBuilder.path("/store/" + STORE_ENTITY_TEST.getId() )
                        .queryParam("currency", "USD")
                        .build())
                .header("Authorization", "Bearer " + USER_TOKEN )
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList( StoreResponseDTO.class );
    }

    @Test
    public void test_storeController_getById_notLogged_returnError() {

        StoreEntity storeEntity = StoreEntity.builder()
                .name("TEST SOTRE 2")
                .city("TEST CITY")
                .state("TEST STATE")
                .address("TEST ADDRESS")
                .description("TEST DESCRIPTION")
                .build();

        webTestClient.get()
                .uri(uriBuilder -> uriBuilder.path("/store/" + STORE_ENTITY_TEST.getId() )
                        .queryParam("currency", "USD")
                        .build())
                .header("Authorization", "Bearer " +"123" )
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isUnauthorized();
    }



}
