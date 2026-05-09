package com.library.catalog.dto.response;

import java.time.Instant;
import java.util.UUID;

public record BookResponse(

        UUID id,

        String title,

        String author,

        String isbn,

        String description,

        String coverImageUrl,

        UUID categoryId,

        String categoryName,

        int totalCopies,

        int availableCopies,

        boolean available,

        Integer publishedYear,

        Instant createdAt,

        Instant updatedAt
) {
}
