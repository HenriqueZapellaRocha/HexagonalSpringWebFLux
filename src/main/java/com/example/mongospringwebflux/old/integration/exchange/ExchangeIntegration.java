package com.example.mongospringwebflux.integration.exchange;



import com.example.mongospringwebflux.exception.NotFoundException;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


@Service
public class ExchangeIntegration {

    @Autowired
    @Qualifier( "test" )
    private WebClient webClient;

    public Mono<Double> makeExchange( String from, String to ) {

        return webClient
                .get()
                .uri( "/pair/{from}/{to}", from, to )
                .retrieve()
                .bodyToMono( ExchangeResponse.class )
                .map( ExchangeResponse::conversion_rate )
                    .onErrorResume( throwable -> Mono.error( new NotFoundException( "Currency not found" ) ) );
    }
}
