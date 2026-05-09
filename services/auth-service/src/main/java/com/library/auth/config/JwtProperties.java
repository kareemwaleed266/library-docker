package com.library.auth.config;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "jwt")
public record JwtProperties(

        @NotBlank(message = "JWT secret must not be blank")
        String secret,

        @Min(value = 1, message = "JWT expiration minutes must be at least 1")
        long expirationMinutes
) {
}