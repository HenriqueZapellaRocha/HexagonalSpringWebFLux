package com.example.mongospringwebflux.infrastructure.configs.minio;

import io.minio.MinioAsyncClient;
import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MinioClientConfig {


    @Value( "${minio.url}" )
    private String endpoint;
    @Value( "${minio.access.key}" )
    private String accessKey;
    @Value( "${minio.secret.key}" )
    private String secretKey;

    @Bean
    public MinioAsyncClient minioClient() {

        return MinioAsyncClient.builder()
                .endpoint( endpoint )
                .credentials( accessKey, secretKey )
                .build();
    }
}
