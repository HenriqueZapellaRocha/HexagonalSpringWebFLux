package com.example.mongospringwebflux;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



@SpringBootApplication
@OpenAPIDefinition(
        info = @Info(title = "Spring Web FLux Stores API", version = "1.0",
                description = "This is a documentantion of: https://github.com/HenriqueZapellaRocha/MongoSpringWebFlux ") )
public class MongoSpringWebFluxApplication {

    public static void main(String[] args) {
        SpringApplication.run(MongoSpringWebFluxApplication.class, args);
    }

}
