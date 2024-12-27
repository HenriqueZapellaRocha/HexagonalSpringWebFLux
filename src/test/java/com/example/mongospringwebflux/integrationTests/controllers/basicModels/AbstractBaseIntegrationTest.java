package com.example.mongospringwebflux.integrationTests.controllers.basicModels;

import com.example.mongospringwebflux.integration.exchange.ExchangeIntegration;
import com.example.mongospringwebflux.repository.ProductRepository;
import com.example.mongospringwebflux.repository.StoreRepository;
import com.example.mongospringwebflux.repository.UserRepository;
import com.example.mongospringwebflux.repository.entity.ProductEntity;
import com.example.mongospringwebflux.repository.entity.StoreEntity;
import com.example.mongospringwebflux.repository.entity.UserEntity;
import com.example.mongospringwebflux.repository.entity.enums.UserRoles;
import com.example.mongospringwebflux.service.services.securityServices.TokenService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import java.math.BigDecimal;

@Testcontainers
@AutoConfigureWebTestClient
@SpringBootTest
public class AbstractBaseIntegrationTest {

    @Autowired
    public WebTestClient webTestClient;

    @MockBean
    public ExchangeIntegration exchangeIntegration;

    @Container
    @ServiceConnection
    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:latest");

    @Autowired
    protected ProductRepository productRepository;

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected StoreRepository storeRepository;

    @Autowired
    public TokenService tokenService;

    public String USER_TOKEN;

    public String STORE_ADMIN_TOKEN;

    public String ADMIN_TOKEN;

    public StoreEntity STORE_ENTITY_TEST;

    public UserEntity USER_ADMIN;

    public UserEntity USER_NORMAL;

    public UserEntity USER_STORE_ADMIN;

    public ProductEntity PRODUCT_1;
    public ProductEntity PRODUCT_2;
    public ProductEntity PRODUCT_3;




    @BeforeEach
    public void setupProducts() {

        userRepository.deleteAll().block();
        storeRepository.deleteAll().block();
        productRepository.deleteAll().block();

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


        String encodedNormalUserPassword = passwordEncoder.encode("12345");

        UserEntity normalUser = UserEntity.builder()
                .login("NORMAL USER")
                .password(encodedNormalUserPassword)
                .storeId(null)
                .role(UserRoles.ROLE_USER)
                .build();

        USER_NORMAL = userRepository.save(normalUser).block();

        StoreEntity store = StoreEntity.builder()
                .name("STORE TEST")
                .description("THIS IS A TEST STORE")
                .city("PORTO ALEGRE")
                .address("SANTA MARIA 490")
                .state("RIO GRANDE DO SUL")
                .build();

        STORE_ENTITY_TEST = storeRepository.save(store).block();

        String encodedStoreAdminPassword = passwordEncoder.encode("12345");

        UserEntity adminStore = UserEntity.builder()
                .login("STORE ADMIN USER")
                .password(encodedStoreAdminPassword)  // Usando a senha codificada
                .storeId(STORE_ENTITY_TEST.getId())
                .role(UserRoles.ROLE_STORE_ADMIN)
                .build();

        USER_STORE_ADMIN = userRepository.save(adminStore).block();

        String encodedAdminPassword = passwordEncoder.encode("12345");

        UserEntity admin = UserEntity.builder()
                .login("ADMIN USER")
                .role(UserRoles.ROLE_ADMIN)
                .password(encodedAdminPassword)
                .storeId(null)
                .build();

        USER_ADMIN = userRepository.save(admin).block();

        ProductEntity product1 = ProductEntity.builder()
                .name("PRODUCT 1")
                .description("THIS IS A PRODUCT 1 CREATED FOR TESTS")
                .storeId(USER_STORE_ADMIN.getStoreId())
                .price(new BigDecimal(100.0))
                .build();

        ProductEntity product2 = ProductEntity.builder()
                .name("PRODUCT 2")
                .description("THIS IS A PRODUCT 2 CREATED FOR TESTS")
                .storeId(USER_STORE_ADMIN.getStoreId())
                .price(new BigDecimal(200.0))
                .build();

        ProductEntity product3 = ProductEntity.builder()
                .name("PRODUCT 3")
                .description("THIS IS A PRODUCT 3 CREATED FOR TESTS")
                .storeId(USER_STORE_ADMIN.getStoreId())
                .price(new BigDecimal(300.0))
                .build();

        PRODUCT_1 = productRepository.save(product1).block();
        PRODUCT_2 = productRepository.save(product2).block();
        PRODUCT_3 = productRepository.save(product3).block();


        USER_TOKEN = tokenService.generateToke(USER_NORMAL).block();
        STORE_ADMIN_TOKEN = tokenService.generateToke(USER_STORE_ADMIN).block();
        ADMIN_TOKEN = tokenService.generateToke(USER_ADMIN).block();
    }

    @AfterEach
    public void tearDown() {
        ReactiveSecurityContextHolder.clearContext();
    }



}






