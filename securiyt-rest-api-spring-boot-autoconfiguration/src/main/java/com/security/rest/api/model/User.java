package com.security.rest.api.model;

import lombok.Data;

@Data
public class User {

    private String username;
    private String password;
    private int enable;
}
