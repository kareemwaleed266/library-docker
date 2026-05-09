package com.library.engagement.dto.request;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record FavoriteRequest(

        @NotNull(message = "Book id is required")
        UUID bookId
) {
}
