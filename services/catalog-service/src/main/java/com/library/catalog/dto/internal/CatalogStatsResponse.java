package com.library.catalog.dto.internal;

public record CatalogStatsResponse(
        long totalBooks,
        long availableBooks,
        long unavailableBooks,
        long totalCategories
) {
}
