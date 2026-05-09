package com.library.report.controller;

import com.library.common.dto.ApiResponse;
import com.library.report.dto.response.CatalogReportResponse;
import com.library.report.dto.response.CirculationReportResponse;
import com.library.report.dto.response.DashboardReportResponse;
import com.library.report.dto.response.EngagementReportResponse;
import com.library.report.service.ReportService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/dashboard")
    public ApiResponse<DashboardReportResponse> dashboard() {
        return ApiResponse.success(
                "Dashboard report retrieved successfully",
                reportService.dashboard()
        );
    }

    @GetMapping("/catalog")
    public ApiResponse<CatalogReportResponse> catalogReport() {
        return ApiResponse.success(
                "Catalog report retrieved successfully",
                reportService.catalogReport()
        );
    }

    @GetMapping("/circulation")
    public ApiResponse<CirculationReportResponse> circulationReport() {
        return ApiResponse.success(
                "Circulation report retrieved successfully",
                reportService.circulationReport()
        );
    }

    @GetMapping("/engagement")
    public ApiResponse<EngagementReportResponse> engagementReport() {
        return ApiResponse.success(
                "Engagement report retrieved successfully",
                reportService.engagementReport()
        );
    }
}
