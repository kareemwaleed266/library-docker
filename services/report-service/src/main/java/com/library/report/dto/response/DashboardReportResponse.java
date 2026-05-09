package com.library.report.dto.response;

public record DashboardReportResponse(
        CatalogReportResponse catalog,
        CirculationReportResponse circulation,
        EngagementReportResponse engagement
) {
}
