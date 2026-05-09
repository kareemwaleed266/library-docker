package com.library.circulation.dto.request;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record BorrowRequest(

        @NotNull(message = "Book id is required")
        UUID bookId
) {
}
