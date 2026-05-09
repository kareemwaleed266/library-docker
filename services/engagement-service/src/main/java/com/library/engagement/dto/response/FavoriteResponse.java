package com.library.engagement.dto.response;

import java.time.Instant;
import java.util.UUID;

public record FavoriteResponse(
        UUID id,
        UUID userId,
        UUID bookId,
        Instant createdAt
) {
}
