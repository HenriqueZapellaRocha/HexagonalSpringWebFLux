package com.example.mongospringwebflux.infrastructure.configs.documentation;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class openDocCongif {

    @Bean
    public OpenAPI customOpenAPI() {
        final String securitySchemeName = "bearerAuth";
        final String apiTitle = String.format( "%s API", StringUtils.capitalize( "Spring web flux Stores API" ) );

        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
                .components(
                        new Components()
                                .addSecuritySchemes( securitySchemeName,
                                        new SecurityScheme()
                                                .name( securitySchemeName )
                                                .type( SecurityScheme.Type.HTTP )
                                                .scheme( "bearer" )
                                                .bearerFormat( "JWT" )
                                )
                )
                .info( new Info().title( apiTitle ).version( "1" ) );
    }
}
