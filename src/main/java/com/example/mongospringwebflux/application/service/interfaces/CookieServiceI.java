package com.example.mongospringwebflux.application.service.interfaces;

import org.springframework.http.server.reactive.ServerHttpResponse;

public interface CookieServiceI {

    public void setCookie(ServerHttpResponse response, String value );
}
