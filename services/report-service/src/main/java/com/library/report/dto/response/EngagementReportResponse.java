package com.library.report.dto.response;

public record EngagementReportResponse(
        long totalFavorites,
        long totalReviews,
        double averageRating
) {
}
