package com.example.service.services;


import com.example.service.interfaces.CookieServiceI;
import org.springframework.http.ResponseCookie;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;



@Service
@Component
public class CookieService implements CookieServiceI {

    public void setCookie( ServerHttpResponse response, String value ) {
        ResponseCookie cookie = ResponseCookie.from( "last", value )
                .httpOnly( true )
                .path( "/product/last" )
                .build();

        response.addCookie(cookie);
    }
}


