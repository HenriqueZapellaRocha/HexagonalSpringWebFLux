package com.example.mongospringwebflux.integrationTests.controllers;

import com.example.mongospringwebflux.dtos.GlobalExceptionDTO;
import com.example.mongospringwebflux.integrationTests.controllers.basicModels.AbstractBaseIntegrationTest;
import com.example.mongospringwebflux.repository.entity.enums.UserRoles;
import com.example.mongospringwebflux.v1.controller.DTOS.requests.RegisterRequestDTO;
import com.example.mongospringwebflux.v1.controller.DTOS.requests.StoreCreationRequestDTO;
import com.example.mongospringwebflux.v1.controller.DTOS.requests.loginRequestDTO;
import com.example.mongospringwebflux.v1.controller.DTOS.responses.AuthResponseDTO;
import com.example.mongospringwebflux.v1.controller.DTOS.responses.RegisterResponseDTO;
import org.junit.jupiter.api.Test;

import java.util.Objects;


public class AuthControllerTest extends AbstractBaseIntegrationTest {


    @Test
    public void test_loginUser_validUser_returnOKAndJWT() {

        loginRequestDTO loginRequest = loginRequestDTO.builder()
                .login(USER_NORMAL.getLogin())
                .password("12345")
                .build();


        webTestClient.post()
                .uri(uriBuilder -> uriBuilder.path("/auth/login")
                        .build())
                .bodyValue(loginRequest)
                .exchange()
                .expectStatus().isOk()
                .expectBody(AuthResponseDTO.class)
                .consumeWith(authResponse -> {

                    AuthResponseDTO authResponseDTO = authResponse.getResponseBody();

                    assert authResponseDTO != null;
                    assert authResponseDTO.username().equals(USER_NORMAL.getUsername());
                    assert tokenService.validateToke(authResponseDTO.token())
                            .block().equals(loginRequest.login());
                });

        loginRequestDTO loginRequestStore = loginRequestDTO.builder()
                .login(USER_STORE_ADMIN.getLogin())
                .password("12345")
                .build();

        webTestClient.post()
                .uri(uriBuilder -> uriBuilder.path("/auth/login")
                        .build())
                .bodyValue(loginRequestStore)
                .exchange()
                .expectStatus().isOk()
                .expectBody(AuthResponseDTO.class)
                .consumeWith(authResponse -> {

                    AuthResponseDTO authResponseDTO = authResponse.getResponseBody();

                    assert authResponseDTO != null;
                    assert authResponseDTO.username().equals(USER_STORE_ADMIN.getLogin());
                    assert tokenService.validateToke(authResponseDTO.token())
                            .block().equals(loginRequestStore.login());
                });

        loginRequestDTO loginRequestAdmin = loginRequestDTO.builder()
                .login(USER_ADMIN.getLogin())
                .password("12345")
                .build();

        webTestClient.post()
                .uri(uriBuilder -> uriBuilder.path("/auth/login")
                        .build())
                .bodyValue(loginRequestStore)
                .exchange()
                .expectStatus().isOk()
                .expectBody(AuthResponseDTO.class)
                .consumeWith(authResponse -> {

                    AuthResponseDTO authResponseDTO = authResponse.getResponseBody();

                    assert authResponseDTO != null;
                    assert authResponseDTO.username().equals(USER_ADMIN.getLogin());
                    assert tokenService.validateToke(authResponseDTO.token())
                            .block().equals(loginRequestAdmin.login());
                });
    }

    @Test
    public void test_loginUser_badCredentials_returnErrorAndMessage() {

        loginRequestDTO loginRequest = loginRequestDTO.builder()
                .login("1")
                .password("1")
                .build();

        webTestClient.post()
                .uri(uriBuilder -> uriBuilder.path("/auth/login")
                        .build())
                .bodyValue(loginRequest)
                .exchange()
                .expectStatus().isUnauthorized()
                .expectBody(GlobalExceptionDTO.class)
                .consumeWith(globalException -> {
                    GlobalExceptionDTO globalExceptionDTO = globalException.getResponseBody();

                    assert globalExceptionDTO != null;
                    assert globalExceptionDTO.getMessage().equals("Invalid username or password");
                });
    }

    @Test
    public void test_registerUser_ValidRegisters_returnOKAndMessage() {

        //normal user register
        RegisterRequestDTO normalUserRegister = RegisterRequestDTO.builder()
                .login("TEST NORMAL USER")
                .password("123")
                .role(UserRoles.ROLE_USER)
                .build();

        webTestClient.post()
                .uri(uriBuilder -> uriBuilder.path("/auth/register")
                        .build())
                .bodyValue(normalUserRegister)
                .exchange()
                .expectStatus().isOk()
                .expectBody(RegisterResponseDTO.class)
                .consumeWith(globalException -> {
                    RegisterResponseDTO registerResponseDTO = globalException.getResponseBody();

                    assert registerResponseDTO.login().equals(normalUserRegister.login());

                    assert userRepository.findByLogin(registerResponseDTO.login()).block() != null;
                });

        //store admin user register
        RegisterRequestDTO storeAdminUserRegister = RegisterRequestDTO.builder()
                .login("TEST STORE USER")
                .password("123")
                .role(UserRoles.ROLE_STORE_ADMIN)
                .storeRelated(StoreCreationRequestDTO.builder()
                        .name("testStore")
                        .state("testState")
                        .address("testAdress")
                        .description("testDescription")
                        .city("testCity")
                        .build())
                .build();

        webTestClient.post()
                .uri(uriBuilder -> uriBuilder.path("/auth/register")
                        .build())
                .bodyValue(storeAdminUserRegister)
                .exchange()
                .expectStatus().isOk()
                .expectBody(RegisterResponseDTO.class)
                .consumeWith(globalException -> {
                    RegisterResponseDTO registerResponseDTO = globalException.getResponseBody();

                    assert registerResponseDTO.login().equals(storeAdminUserRegister.login());

                    assert userRepository.findByLogin(registerResponseDTO.login()).block() != null;

                });

        //store admin user register
        RegisterRequestDTO adminUserRegister = RegisterRequestDTO.builder()
                .login("TEST ADMIN USER")
                .password("123")
                .role(UserRoles.ROLE_ADMIN)
                .build();

        webTestClient.post()
                .uri(uriBuilder -> uriBuilder.path("/auth/register")
                        .build())
                .bodyValue(adminUserRegister)
                .exchange()
                .expectStatus().isOk()
                .expectBody(RegisterResponseDTO.class)
                .consumeWith(globalException -> {
                    RegisterResponseDTO registerResponseDTO = globalException.getResponseBody();

                    assert registerResponseDTO.login().equals(adminUserRegister.login());

                    assert userRepository.findByLogin(registerResponseDTO.login()).block() != null;

                });
    }

    @Test
    public void test_registerUser_userAlreadyExist_returnErrorAndMessage() {

        //user already exist
        RegisterRequestDTO normalUserRegister = RegisterRequestDTO.builder()
                .login(USER_ADMIN.getLogin())
                .password("123")
                .role(UserRoles.ROLE_USER)
                .build();

        webTestClient.post()
                .uri(uriBuilder -> uriBuilder.path("/auth/register")
                        .build())
                .bodyValue(normalUserRegister)
                .exchange()
                .expectStatus().isUnauthorized()
                .expectBody(GlobalExceptionDTO.class)
                .consumeWith(globalException -> {

                    GlobalExceptionDTO exceptionDTO = globalException.getResponseBody();

                    assert exceptionDTO.getMessage().equals("User already exists");
                });
    }

    @Test
    public void test_registerUser_storeAlreadyExist_returnErrorAndMessage() {

        RegisterRequestDTO normalUserRegister = RegisterRequestDTO.builder()
                .login("clang")
                .password("123")
                .role(UserRoles.ROLE_STORE_ADMIN)
                .storeRelated(
                        StoreCreationRequestDTO.builder()
                                .name(STORE_ENTITY_TEST.getName())
                                .state(STORE_ENTITY_TEST.getState())
                                .address(STORE_ENTITY_TEST.getAddress())
                                .description(STORE_ENTITY_TEST.getDescription())
                                .city(STORE_ENTITY_TEST.getCity())
                                .build()
                ).build();

        webTestClient.post()
                .uri(uriBuilder -> uriBuilder.path("/auth/register")
                        .build())
                .bodyValue(normalUserRegister)
                .exchange()
                .expectStatus().isUnauthorized()
                .expectBody( GlobalExceptionDTO.class )
                .consumeWith(globalException -> {

                    GlobalExceptionDTO exceptionDTO = globalException.getResponseBody();

                    assert Objects.requireNonNull(exceptionDTO).getMessage().equals( "This store already exists" );
                });

    }
}
