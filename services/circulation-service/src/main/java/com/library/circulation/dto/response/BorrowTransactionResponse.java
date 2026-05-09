package com.library.circulation.dto.response;

import com.library.circulation.enums.BorrowStatus;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record BorrowTransactionResponse(
        UUID id,
        UUID userId,
        UUID bookId,
        BorrowStatus status,
        Instant borrowDate,
        Instant dueDate,
        Instant returnedDate,
        BigDecimal fineAmount,
        UUID approvedBy,
        Instant createdAt,
        Instant updatedAt
) {
}
