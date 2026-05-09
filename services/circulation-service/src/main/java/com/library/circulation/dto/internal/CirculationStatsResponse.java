package com.library.circulation.dto.internal;

public record CirculationStatsResponse(
        long totalBorrowRequests,
        long pendingBorrows,
        long approvedBorrows,
        long returnedBorrows,
        long overdueBorrows,
        long activeReservations
) {
}
