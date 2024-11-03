package com.cypsolabs.recipefinder_api.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthenticationResponse {
    private final String token;
    private final int id;
    private final String username;

    public AuthenticationResponse(String token, int userId, String name) {
        this.token = token;
        id = userId;
        this.username = name;
    }
}
