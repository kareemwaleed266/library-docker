package com.library.engagement.dto.response;

import java.time.Instant;
import java.util.UUID;

public record ReviewResponse(
        UUID id,
        UUID userId,
        UUID bookId,
        int rating,
        String comment,
        Instant createdAt,
        Instant updatedAt
) {
}
