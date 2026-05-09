package com.library.report.service;

import com.library.report.dto.response.CatalogReportResponse;
import com.library.report.dto.response.CirculationReportResponse;
import com.library.report.dto.response.DashboardReportResponse;
import com.library.report.dto.response.EngagementReportResponse;

public interface ReportService {

    DashboardReportResponse dashboard();

    CatalogReportResponse catalogReport();

    CirculationReportResponse circulationReport();

    EngagementReportResponse engagementReport();
}
