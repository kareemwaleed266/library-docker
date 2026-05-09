package com.library.catalog.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoryRequest(

        @NotBlank(message = "Category name is required")
        @Size(min = 2, max = 120, message = "Category name must be between 2 and 120 characters")
        String name,

        @Size(max = 1000, message = "Category description must not exceed 1000 characters")
        String description
) {
}