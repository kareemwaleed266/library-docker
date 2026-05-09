package com.library.circulation.dto.response;

import com.library.circulation.enums.ReservationStatus;

import java.time.Instant;
import java.util.UUID;

public record ReservationResponse(
        UUID id,
        UUID userId,
        UUID bookId,
        ReservationStatus status,
        int queueOrder,
        Instant reservationDate,
        Instant expiryDate,
        Instant createdAt,
        Instant updatedAt
) {
}
