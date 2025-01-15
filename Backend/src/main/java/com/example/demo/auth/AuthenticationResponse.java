package com.example.demo.auth;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AuthenticationResponse(
        @JsonProperty("id_token")
        String idToken
) {
}
