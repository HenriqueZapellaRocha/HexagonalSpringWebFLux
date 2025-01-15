package com.example.mongospringwebflux.domain.store;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Store {

    private String id;
    private String name;
    private String description;
    private String address;
    private String city;
    private String state;
}
