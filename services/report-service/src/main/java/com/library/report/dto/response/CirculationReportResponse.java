package com.library.report.dto.response;

public record CirculationReportResponse(
        long totalBorrowRequests,
        long pendingBorrows,
        long approvedBorrows,
        long returnedBorrows,
        long overdueBorrows,
        long activeReservations
) {
}
