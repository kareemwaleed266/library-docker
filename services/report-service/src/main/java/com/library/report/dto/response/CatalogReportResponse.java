package com.library.report.dto.response;

public record CatalogReportResponse(
        long totalBooks,
        long availableBooks,
        long unavailableBooks,
        long totalCategories
) {
}
