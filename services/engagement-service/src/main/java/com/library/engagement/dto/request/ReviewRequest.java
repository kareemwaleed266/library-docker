package com.library.engagement.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record ReviewRequest(

        @NotNull(message = "Book id is required")
        UUID bookId,

        @Min(value = 1, message = "Rating must be at least 1")
        @Max(value = 5, message = "Rating must be at most 5")
        int rating,

        @Size(max = 2000, message = "Comment must not exceed 2000 characters")
        String comment
) {
}
