package com.example.service.ports;

import org.springframework.http.server.reactive.ServerHttpResponse;

public interface CookieServicePort {

    public void setCookie(ServerHttpResponse response, String value );
}
