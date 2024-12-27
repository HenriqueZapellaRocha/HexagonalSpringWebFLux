package com.example.mongospringwebflux.integrationTests.controllers;

import com.example.mongospringwebflux.dtos.InvalidInputValuesExceptionDTO;
import com.example.mongospringwebflux.dtos.NotFoundExceptionDTO;
import com.example.mongospringwebflux.exception.NotFoundException;
import com.example.mongospringwebflux.integrationTests.controllers.basicModels.AbstractBaseIntegrationTest;
import com.example.mongospringwebflux.repository.entity.ProductEntity;
import com.example.mongospringwebflux.v1.controller.DTOS.requests.ProductRequestDTO;
import com.example.mongospringwebflux.v1.controller.DTOS.responses.ProductResponseDTO;
import org.springframework.http.MediaType;
import org.testcontainers.shaded.org.apache.commons.lang3.RandomStringUtils;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;



public class productControllerTest extends AbstractBaseIntegrationTest {


    @Test
    public void test_getAllProducts_UnauthorizedUser_returnUnauthorized() {

        webTestClient.get()
                .uri(uriBuilder -> uriBuilder.path("/product/All")
                        .queryParam("currency", "USD")
                        .build())
                .header("Authorization", "Bearer " +
                        RandomStringUtils.randomAlphanumeric(30))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isUnauthorized();
    }

    @Test
    public void test_GetAllProducts_AuthorizedUser_returnOk() {
        ProductResponseDTO PRODUCT_REQUEST_1 = ProductResponseDTO.entityToResponse(PRODUCT_1, "USD");
        ProductResponseDTO PRODUCT_REQUEST_2 = ProductResponseDTO.entityToResponse(PRODUCT_2, "USD");
        ProductResponseDTO PRODUCT_REQUEST_3 = ProductResponseDTO.entityToResponse(PRODUCT_3, "USD");

        when(exchangeIntegration.makeExchange(anyString(), anyString()))
                .thenReturn(Mono.just(1.0 ) );

        webTestClient.get()
                .uri(uriBuilder -> uriBuilder.path("/product/All")
                        .queryParam("currency", "USD")
                        .build())
                .header("Authorization", "Bearer " + ADMIN_TOKEN)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(ProductResponseDTO.class)
                .consumeWith(response -> {
                    List<ProductResponseDTO> products = response.getResponseBody();

                    assert products != null;
                    assert products.contains( PRODUCT_REQUEST_1 );
                    assert products.contains( PRODUCT_REQUEST_2 );
                    assert products.contains( PRODUCT_REQUEST_3 );


                });

        webTestClient.get()
                .uri(uriBuilder -> uriBuilder.path("/product/All")
                        .queryParam("currency", "USD")
                        .build())
                .header("Authorization", "Bearer " + USER_TOKEN)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(ProductResponseDTO.class)
                .consumeWith(response -> {
                    List<ProductResponseDTO> products = response.getResponseBody();

                    assert products != null;
                    assert products.contains( PRODUCT_REQUEST_1 );
                    assert products.contains( PRODUCT_REQUEST_2 );
                    assert products.contains( PRODUCT_REQUEST_3 );


                });

        webTestClient.get()
                .uri(uriBuilder -> uriBuilder.path("/product/All")
                        .queryParam("currency", "USD")
                        .build())
                .header("Authorization", "Bearer " + STORE_ADMIN_TOKEN)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(ProductResponseDTO.class)
                .consumeWith(response -> {
                    List<ProductResponseDTO> products = response.getResponseBody();

                    assert products != null;
                    assert products.contains( PRODUCT_REQUEST_1 );
                    assert products.contains( PRODUCT_REQUEST_2 );
                    assert products.contains( PRODUCT_REQUEST_3 );
                });
    }

    @Test
    public void test_GetAllProducts_AuthorizedUser_returnUnauthorized() {

        ProductResponseDTO PRODUCT_REQUEST_1 = ProductResponseDTO.entityToResponse(PRODUCT_1, "USD");
        ProductResponseDTO PRODUCT_REQUEST_2 = ProductResponseDTO.entityToResponse(PRODUCT_2, "USD");
        ProductResponseDTO PRODUCT_REQUEST_3 = ProductResponseDTO.entityToResponse(PRODUCT_3, "USD");

        webTestClient.get()
                .uri(uriBuilder -> uriBuilder.path("/product/All")
                        .build())
                .header("Authorization", "Bearer " + ADMIN_TOKEN)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isUnauthorized();
    }

    @Test
    public void test_GetAllProducts_AuthorizedUser_() {
        when(exchangeIntegration.makeExchange(anyString(), anyString())).
                thenThrow( new NotFoundException( "Currency not found" ));

        webTestClient.get()
                .uri(uriBuilder -> uriBuilder.path("/product/All")
                        .queryParam("currency", "UZ")
                        .build())
                .header("Authorization", "Bearer " + ADMIN_TOKEN)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isNotFound()
                .expectBodyList( NotFoundExceptionDTO.class )
                .consumeWith( response -> {
                    List<NotFoundExceptionDTO> notFound = response.getResponseBody();

                    assert notFound != null;
                    assert notFound.get( 0 ).getError().equals( "Currency not found" );
                });
    }

    // GetbyId tests

    @Test
    public void test_GetByIdProduct_UnauthorizedUser_returnUnauthorized() {

        webTestClient.get()
                .uri(uriBuilder -> uriBuilder.path("/product/"+PRODUCT_1.getProductID())
                        .queryParam("currency", "USD")
                        .build())
                .header("Authorization", "Bearer " +
                        RandomStringUtils.randomAlphanumeric(30))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isUnauthorized();
    }

    @Test
    public void test_GetByIdProduct_AuthorizedUser_returnOk() {
        ProductResponseDTO PRODUCT_REQUEST_1 = ProductResponseDTO.entityToResponse(PRODUCT_1, "USD");

        when(exchangeIntegration.makeExchange(anyString(), anyString()))
                .thenReturn(Mono.just(1.0 ) );

        webTestClient.get()
                .uri(uriBuilder -> uriBuilder.path("/product/"+PRODUCT_1.getProductID())
                        .queryParam("currency", "USD")
                        .build())
                .header("Authorization", "Bearer " + ADMIN_TOKEN)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(ProductResponseDTO.class)
                .consumeWith(response -> {
                    List<ProductResponseDTO> products = response.getResponseBody();

                    assert products != null;
                    assert products.contains( PRODUCT_REQUEST_1 );
                });

        webTestClient.get()
                .uri(uriBuilder -> uriBuilder.path("/product/"+PRODUCT_1.getProductID())
                        .queryParam("currency", "USD")
                        .build())
                .header("Authorization", "Bearer " + USER_TOKEN)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(ProductResponseDTO.class)
                .consumeWith(response -> {
                    List<ProductResponseDTO> products = response.getResponseBody();

                    assert products != null;
                    assert products.contains( PRODUCT_REQUEST_1 );
                });

        webTestClient.get()
                .uri(uriBuilder -> uriBuilder.path("/product/"+PRODUCT_1.getProductID())
                        .queryParam("currency", "USD")
                        .build())
                .header("Authorization", "Bearer " + STORE_ADMIN_TOKEN)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(ProductResponseDTO.class)
                .consumeWith(response -> {
                    List<ProductResponseDTO> products = response.getResponseBody();

                    assert products != null;
                    assert products.contains( PRODUCT_REQUEST_1 );
                });
    }

    @Test
    public void test_GetByIdProduct_AuthorizedUser_sendWithoutCurrency_returnUnauthorized() {

        webTestClient.get()
                .uri(uriBuilder -> uriBuilder.path("/product/"+PRODUCT_1.getProductID())
                        .build())
                .header("Authorization", "Bearer " + ADMIN_TOKEN)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isUnauthorized();
    }

    @Test
    public void test_GetByIdProduct_AuthorizedUser_sendWithInvalidCurrency_ReturnNotFound() {

        when(exchangeIntegration.makeExchange(anyString(), anyString())).
                thenThrow( new NotFoundException( "Currency not found" ));

        webTestClient.get()
                .uri(uriBuilder -> uriBuilder.path("/product/"+PRODUCT_1.getProductID())
                        .queryParam("currency", "UZ")
                        .build())
                .header("Authorization", "Bearer " + ADMIN_TOKEN)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isNotFound()
                .expectBodyList( NotFoundExceptionDTO.class )
                .consumeWith( response -> {
                    List<NotFoundExceptionDTO> notFound = response.getResponseBody();

                    assert notFound != null;
                    assert notFound.get( 0 ).getError().equals( "Currency not found" );
                });
    }

    @Test
    public void test_GetByIdProduct_dontExistTheProduct_AuthorizedUser_ReturnNotFound() {

        when(exchangeIntegration.makeExchange(anyString(), anyString()))
                .thenReturn(Mono.just(2.0 ) );

        webTestClient.get()
                .uri(uriBuilder -> uriBuilder.path("/product/"+"123")
                        .queryParam("currency", "--")
                        .build())
                .header("Authorization", "Bearer " + ADMIN_TOKEN)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isNotFound()
                .expectBodyList( NotFoundExceptionDTO.class )
                .consumeWith( response -> {
                    List<NotFoundExceptionDTO> notFound = response.getResponseBody();

                    assert notFound != null;
                    assert notFound.get( 0 ).getError().equals( "No product found" );
                });
    }

    // ADD PRODUCTS TESTS

    @Test
    public void test_addProduct_AuthorizedUser_saveProductInDb_ReturnOkAndObjectCreated() {

        when(exchangeIntegration.makeExchange(anyString(), anyString()))
                .thenReturn(Mono.just(2.0 ) );

        ProductRequestDTO productRequestDTO = ProductRequestDTO.builder()
                .name("NEW PRODUCT CREATED")
                .price( new BigDecimal(200) )
                .description("TEST")
                .build();

        ProductEntity productEntity = productRequestDTO.toEntity();

        productEntity.setPrice(productEntity.getPrice().multiply( new BigDecimal( 2.0 ) ));

        webTestClient.post()
                .uri(uriBuilder -> uriBuilder.path( "/product/add" )
                        .queryParam( "currency", "EUR" )
                        .build())
                .header( "Authorization", "Bearer " + STORE_ADMIN_TOKEN )
                .bodyValue(productRequestDTO)
                .accept( MediaType.APPLICATION_JSON )
                .exchange()
                .expectStatus().isOk()
                .expectBodyList( ProductResponseDTO.class )
                .consumeWith( response -> {
                    List<ProductResponseDTO> products = response.getResponseBody();

                    System.out.println(products.get(0));

                    assert products.get( 0 ).name().equals( "NEW PRODUCT CREATED" );
                    assert products.get( 0 ).price().currency().equals( "USD" );
                    assert products.get( 0 ).description().equals( "TEST" );
                    assert products.get( 0 ).price().value().equals( productEntity.getPrice());
                    assert products.get( 0 ).store().equals( USER_STORE_ADMIN.getStoreId() );

                    assert products.get( 0 ).productID()
                            .equals( productRepository
                                    .findById( products.get( 0 ).productID() ).block().getProductID() );

                });
    }

    @Test
    public void test_addProduct_UnauthorizedUser_ReturnUnauthorized() {

        ProductRequestDTO productRequestDTO = ProductRequestDTO.builder()
                .name("NEW PRODUCT CREATED")
                .price( new BigDecimal(200) )
                .description("TEST")
                .build();

        webTestClient.post()
                .uri(uriBuilder -> uriBuilder.path( "/product/add" )
                        .queryParam( "currency", "EUR" )
                        .build())
                .header( "Authorization", "Bearer " + USER_TOKEN )
                .bodyValue(productRequestDTO)
                .accept( MediaType.APPLICATION_JSON )
                .exchange()
                .expectStatus().isUnauthorized();

        webTestClient.post()
                .uri(uriBuilder -> uriBuilder.path( "/product/add" )
                        .queryParam( "currency", "EUR" )
                        .build())
                .header( "Authorization", "Bearer " + ADMIN_TOKEN )
                .bodyValue(productRequestDTO)
                .accept( MediaType.APPLICATION_JSON )
                .exchange()
                .expectStatus().isUnauthorized();
    }

    @Test
    public void test_addProduct_AuthorizedUser_CurrencyNotFound() {

        when(exchangeIntegration.makeExchange(anyString(), anyString())).
                thenThrow( new NotFoundException( "Currency not found" ));

        ProductRequestDTO productRequestDTO = ProductRequestDTO.builder()
                .name("NEW PRODUCT CREATED")
                .price( new BigDecimal(200) )
                .description("TEST")
                .build();

        webTestClient.post()
                .uri(uriBuilder -> uriBuilder.path( "/product/add" )
                        .queryParam( "currency", "--" )
                        .build())
                .header( "Authorization", "Bearer " + STORE_ADMIN_TOKEN )
                .bodyValue(productRequestDTO)
                .accept( MediaType.APPLICATION_JSON )
                .exchange()
                .expectStatus().isNotFound()
                .expectBodyList( NotFoundExceptionDTO.class )
                .consumeWith( response -> {
                    List<NotFoundExceptionDTO> notFound = response.getResponseBody();

                    assert notFound != null;
                    assert notFound.get( 0 ).getError().equals( "Currency not found" );
                });
    }

    @Test
    public void test_addProduct_AuthorizedUser_invalidProduct_returnErrors() {

        ProductRequestDTO productRequestDTO = ProductRequestDTO.builder()
                .name("")
                .price( new BigDecimal(-200) )
                .description("")
                .build();

        webTestClient.post()
                .uri(uriBuilder -> uriBuilder.path( "/product/add" )
                        .queryParam( "currency", "EUR" )
                        .build())
                .header( "Authorization", "Bearer " + STORE_ADMIN_TOKEN )
                .bodyValue(productRequestDTO)
                .accept( MediaType.APPLICATION_JSON )
                .exchange()
                .expectStatus().isBadRequest()
                .expectBodyList( InvalidInputValuesExceptionDTO.class )
                .consumeWith( response -> {
                    List<InvalidInputValuesExceptionDTO> errors = response.getResponseBody();

                    assert errors != null;
                    assert errors.get(0).getErrors().contains( "description: blank description" );
                    assert errors.get(0).getErrors().contains( "name: blank name" );
                    assert errors.get(0).getErrors().contains( "price: negative number" );
                });
    }

    @Test
    public void test_getLast_withValidCookie_ReturnOkWithProduct() {
        String expectedValue = PRODUCT_1.getProductID();

        when(exchangeIntegration.makeExchange(anyString(), anyString()))
                .thenReturn(Mono.just(2.0 ) );

        webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/product/last")
                        .queryParam("currency", "USD")
                        .build())
                .header( "Authorization", "Bearer " + USER_TOKEN )
                .cookie("last", expectedValue)
                .exchange()
                .expectStatus().isOk()
                .expectBody(ProductResponseDTO.class)
                .consumeWith(response -> {
                    ProductResponseDTO productResponse = response.getResponseBody();

                });

        webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/product/last")
                        .queryParam("currency", "USD")
                        .build())
                .header( "Authorization", "Bearer " + ADMIN_TOKEN )
                .cookie("last", expectedValue)
                .exchange()
                .expectStatus().isOk()
                .expectBody(ProductResponseDTO.class)
                .consumeWith(response -> {
                    ProductResponseDTO productResponse = response.getResponseBody();

                });

        webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/product/last")
                        .queryParam("currency", "USD")
                        .build())
                .header( "Authorization", "Bearer " + STORE_ADMIN_TOKEN )
                .cookie("last", expectedValue)
                .exchange()
                .expectStatus().isOk()
                .expectBody(ProductResponseDTO.class)
                .consumeWith(response -> {
                    ProductResponseDTO productResponse = response.getResponseBody();

                });
    }

    @Test
    public void test_getLast_withInvalidCookie_ReturnError() {
        String expectedValue = PRODUCT_1.getProductID();

        when(exchangeIntegration.makeExchange(anyString(), anyString()))
                .thenReturn(Mono.just(2.0 ) );

        webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/product/last")
                        .queryParam("currency", "USD")
                        .build())
                .header( "Authorization", "Bearer " + USER_TOKEN )
                .cookie("last", "12")
                .exchange()
                .expectStatus().isNotFound();

        webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/product/last")
                        .queryParam("currency", "USD")
                        .build())
                .header( "Authorization", "Bearer " + ADMIN_TOKEN )
                .cookie("last", "12")
                .exchange()
                .expectStatus().isNotFound();

        webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/product/last")
                        .queryParam("currency", "USD")
                        .build())
                .header( "Authorization", "Bearer " + STORE_ADMIN_TOKEN )
                .cookie("last", "12")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    public void test_StoreRelatedProducts_returnOkAndProducts() {

        ProductResponseDTO PRODUCT_REQUEST_1 = ProductResponseDTO.entityToResponse(PRODUCT_1, "USD");
        ProductResponseDTO PRODUCT_REQUEST_2 = ProductResponseDTO.entityToResponse(PRODUCT_2, "USD");
        ProductResponseDTO PRODUCT_REQUEST_3 = ProductResponseDTO.entityToResponse(PRODUCT_3, "USD");

        when(exchangeIntegration.makeExchange(anyString(), anyString()))
                .thenReturn(Mono.just(1.0 ) );

        webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path( "/product/storeRelated/" + STORE_ENTITY_TEST.getId() )
                        .queryParam("currency", "USD")
                        .build())
                .header( "Authorization", "Bearer " + STORE_ADMIN_TOKEN )
                .exchange()
                .expectStatus().isOk()
                .expectBodyList( ProductResponseDTO.class )
                .consumeWith( response -> {
                    List<ProductResponseDTO> products = response.getResponseBody();

                    assert products.contains( PRODUCT_REQUEST_1 );
                    assert products.contains( PRODUCT_REQUEST_2 );
                    assert products.contains( PRODUCT_REQUEST_3 );

                });

    }

    @Test
    public void test_StoreRelatedProducts_authorizedUser_returnNotFound() {

        ProductResponseDTO PRODUCT_REQUEST_1 = ProductResponseDTO.entityToResponse(PRODUCT_1, "USD");
        ProductResponseDTO PRODUCT_REQUEST_2 = ProductResponseDTO.entityToResponse(PRODUCT_2, "USD");
        ProductResponseDTO PRODUCT_REQUEST_3 = ProductResponseDTO.entityToResponse(PRODUCT_3, "USD");

        when(exchangeIntegration.makeExchange(anyString(), anyString()))
                .thenReturn(Mono.just(1.0 ) );

        webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path( "/product/storeRelated/" + "123" )
                        .queryParam("currency", "USD")
                        .build())
                .header( "Authorization", "Bearer " + STORE_ADMIN_TOKEN )
                .exchange()
                .expectStatus().isNotFound()
                .expectBody( NotFoundExceptionDTO.class )
                .consumeWith( response -> {
                    NotFoundExceptionDTO message = response.getResponseBody();

                    assert message.getError().equals( "No product found" );

                });
    }

    @Test
    public void test_StoreRelatedProducts_authorizedUser_withoutCurrency_returnUnathorized() {

        ProductResponseDTO PRODUCT_REQUEST_1 = ProductResponseDTO.entityToResponse( PRODUCT_1, "USD" );
        ProductResponseDTO PRODUCT_REQUEST_2 = ProductResponseDTO.entityToResponse( PRODUCT_2, "USD" );
        ProductResponseDTO PRODUCT_REQUEST_3 = ProductResponseDTO.entityToResponse( PRODUCT_3, "USD" );

        when( exchangeIntegration.makeExchange( anyString(), anyString() ) )
                .thenReturn(Mono.just(1.0 ) );

        webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path( "/product/storeRelated/" + STORE_ENTITY_TEST.getId() )
                        .build())
                .header( "Authorization", "Bearer " + STORE_ADMIN_TOKEN )
                .exchange()
                .expectStatus().isUnauthorized();
    }

    @Test
    public void test_UpdateProducts_authorizedUser_returnOkAndProductUpdated() {

        ProductRequestDTO productRequest = ProductRequestDTO.builder()
                .name("PRODUCT UPDATED")
                .description("NEW DESCRIPTION")
                .price(new BigDecimal(200.00))
                .build();

        ProductEntity productEntity = productRequest.toEntity( PRODUCT_1.getProductID() );
        productEntity.setStoreId( PRODUCT_1.getStoreId() );

        ProductResponseDTO productResponseDTO = ProductResponseDTO.entityToResponse(productEntity, "USD");

        when(exchangeIntegration.makeExchange(anyString(), anyString()))
                .thenReturn(Mono.just(1.0 ) );

        webTestClient.put()
                .uri(uriBuilder -> uriBuilder
                        .path( "/product/" + PRODUCT_1.getProductID() )
                        .queryParam("currency", "USD")
                        .build())
                .header( "Authorization", "Bearer " + STORE_ADMIN_TOKEN )
                .bodyValue(  productRequest )
                .exchange()
                .expectStatus().isOk()
                .expectBody( ProductResponseDTO.class )
                .consumeWith( response -> {
                    ProductResponseDTO productResponse = response.getResponseBody();

                    assert productResponse.equals( productResponseDTO );
                });
    }

    @Test
    public void test_UpdateProducts_unathorizedUser_returnUnathorized() {

        ProductRequestDTO productRequest = ProductRequestDTO.builder()
                .name("PRODUCT UPDATED")
                .description("NEW DESCRIPTION")
                .price(new BigDecimal(200.00))
                .build();

        ProductEntity productEntity = productRequest.toEntity( PRODUCT_1.getProductID() );
        productEntity.setStoreId( PRODUCT_1.getStoreId() );

        ProductResponseDTO productResponseDTO = ProductResponseDTO.entityToResponse(productEntity, "USD");

        when(exchangeIntegration.makeExchange(anyString(), anyString()))
                .thenReturn(Mono.just(1.0 ) );

        webTestClient.put()
                .uri(uriBuilder -> uriBuilder
                        .path( "/product/" + PRODUCT_1.getProductID() )
                        .queryParam("currency", "USD")
                        .build())
                .header( "Authorization", "Bearer " + USER_TOKEN )
                .bodyValue(  productRequest )
                .exchange()
                .expectStatus().isUnauthorized();

        webTestClient.put()
                .uri(uriBuilder -> uriBuilder
                        .path( "/product/" + PRODUCT_1.getProductID() )
                        .queryParam("currency", "USD")
                        .build())
                .header( "Authorization", "Bearer " + ADMIN_TOKEN )
                .bodyValue(  productRequest )
                .exchange()
                .expectStatus().isUnauthorized();
    }

    @Test
    public void test_UpdateProducts_authorizedUser_invalidProduct_returnOkAndProductUpdated() {

        ProductRequestDTO productRequest = ProductRequestDTO.builder()
                .name("PRODUCT UPDATED")
                .description("NEW DESCRIPTION")
                .price(new BigDecimal(200.00))
                .build();

        ProductEntity productEntity = productRequest.toEntity( PRODUCT_1.getProductID() );
        productEntity.setStoreId( PRODUCT_1.getStoreId() );

        ProductResponseDTO productResponseDTO = ProductResponseDTO.entityToResponse(productEntity, "USD");

        when(exchangeIntegration.makeExchange(anyString(), anyString()))
                .thenReturn( Mono.just(1.0 ) );

        webTestClient.put()
                .uri(uriBuilder -> uriBuilder
                        .path( "/product/" + "123" )
                        .queryParam("currency", "USD")
                        .build())
                .header( "Authorization", "Bearer " + STORE_ADMIN_TOKEN )
                .bodyValue(  productRequest )
                .exchange()
                .expectStatus().isNotFound()
                .expectBody( NotFoundExceptionDTO.class )
                .consumeWith( response -> {
                    NotFoundExceptionDTO message = response.getResponseBody();

                    assert message.getError().equals( "No product found" );
                });
    }

}

