package com.example.demo.auth;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record AuthenticationRequest(
        @NotNull
        @Size(min = 1, max = 50)
        String username,

        @NotNull
        @Size(min = 4, max = 100)
        String password,
        boolean rememberMe
) {
}