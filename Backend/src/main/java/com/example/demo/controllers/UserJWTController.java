//package com.example.demo.controllers;
//
//import com.example.demo.domain.dto.AuthenticationRequest;
//import com.example.demo.security.jwt.JWTFilter;
//import com.example.demo.security.jwt.TokenProvider;
//import com.example.demo.services.UserService;
//import com.fasterxml.jackson.annotation.JsonProperty;
//import jakarta.validation.Valid;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//
//@RestController
//@RequestMapping("/api/v1.0")
//@RequiredArgsConstructor
//public class UserJWTController {
//
//    private final TokenProvider tokenProvider;
//
//    private final AuthenticationManagerBuilder authenticationManagerBuilder;
//
//    private final UserService userService;
//
//    @PostMapping("/authenticate")
//    public ResponseEntity<JWTToken> authorize(@Valid @RequestBody AuthenticationRequest authenticationRequest) {
//        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
//                authenticationRequest.getUsername(), authenticationRequest.getPassword());
//        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//
//        String jwt = tokenProvider.createToken(authentication, authenticationRequest.isRememberMe());
//
//        userService.updateLoginInfo();
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.add(JWTFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);
//        return new ResponseEntity<>(new JWTToken(jwt), httpHeaders, HttpStatus.OK);
//    }
//
//    static class JWTToken {
//
//        private String idToken;
//
//        JWTToken(String idToken) {
//            this.idToken = idToken;
//        }
//
//        @JsonProperty("id_token")
//        String getIdToken() {
//            return idToken;
//        }
//
//        void setIdToken(String idToken) {
//            this.idToken = idToken;
//        }
//    }
//
//}
