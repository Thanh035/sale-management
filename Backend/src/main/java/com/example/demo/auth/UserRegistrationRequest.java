package com.example.demo.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UserRegistrationRequest(
        @NotNull
        @Size(max = 100)
        String fullName,
        @NotBlank
        @Email
        @Size(min = 5, max = 254)
        String email,
        @NotNull
        @Size(min = 4, max = 100)
        String password
) {
}
