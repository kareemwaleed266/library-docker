package com.library.catalog.config;

import jakarta.validation.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "cloudinary")
public record CloudinaryProperties(

        @NotBlank(message = "Cloudinary cloud name must not be blank")
        String cloudName,

        @NotBlank(message = "Cloudinary api key must not be blank")
        String apiKey,

        @NotBlank(message = "Cloudinary api secret must not be blank")
        String apiSecret,

        String folder
) {
}
