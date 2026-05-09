package com.library.auth.config;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "seed.admin")
public record AdminSeedProperties(

        boolean enabled,

        @NotBlank(message = "Seed admin full name must not be blank")
        String fullName,

        @NotBlank(message = "Seed admin email must not be blank")
        @Email(message = "Seed admin email format is invalid")
        String email,

        String phone,

        @NotBlank(message = "Seed admin password must not be blank")
        String password
) {
}