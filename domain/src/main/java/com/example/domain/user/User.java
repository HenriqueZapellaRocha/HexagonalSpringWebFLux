package com.example.domain.user;

import com.example.domain.enums.UserRoles;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class User {

    private String id;
    private String login;
    private String password;
    private UserRoles role;
    private String storeId;

}
