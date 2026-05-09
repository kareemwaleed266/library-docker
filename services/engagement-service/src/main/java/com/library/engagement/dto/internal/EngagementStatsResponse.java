package com.library.engagement.dto.internal;

public record EngagementStatsResponse(
        long totalFavorites,
        long totalReviews,
        double averageRating
) {
}
