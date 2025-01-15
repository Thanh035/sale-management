package com.example.demo.config;

import org.springframework.stereotype.Component;

@Component
public class SupportProperties {

    public SupportProperties() {
    }

    private final Security security = new Security();

    public Security getSecurity() {
        return this.security;
    }

    public static class Security {
        private final String contentSecurityPolicy = "default-src 'self'; frame-src 'self' data:; script-src 'self' 'unsafe-inline' 'unsafe-eval' https://storage.googleapis.com; style-src 'self' 'unsafe-inline'; img-src 'self' data:; font-src 'self' data:";

        public Security() {
        }

        public String getContentSecurityPolicy() {
            return this.contentSecurityPolicy;
        }
    }
}
