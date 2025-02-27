package com.example.service.interfaces;

import org.springframework.http.server.reactive.ServerHttpResponse;

public interface CookieServiceI {

    public void setCookie(ServerHttpResponse response, String value );
}
