package com.library.report.service.impl;

import com.library.report.client.CatalogReportClient;
import com.library.report.client.CirculationReportClient;
import com.library.report.client.EngagementReportClient;
import com.library.report.dto.response.CatalogReportResponse;
import com.library.report.dto.response.CirculationReportResponse;
import com.library.report.dto.response.DashboardReportResponse;
import com.library.report.dto.response.EngagementReportResponse;
import com.library.report.service.ReportService;
import org.springframework.stereotype.Service;

@Service
public class ReportServiceImpl implements ReportService {

    private final CatalogReportClient catalogReportClient;
    private final CirculationReportClient circulationReportClient;
    private final EngagementReportClient engagementReportClient;

    public ReportServiceImpl(
            CatalogReportClient catalogReportClient,
            CirculationReportClient circulationReportClient,
            EngagementReportClient engagementReportClient
    ) {
        this.catalogReportClient = catalogReportClient;
        this.circulationReportClient = circulationReportClient;
        this.engagementReportClient = engagementReportClient;
    }

    @Override
    public DashboardReportResponse dashboard() {
        return new DashboardReportResponse(
                catalogReport(),
                circulationReport(),
                engagementReport()
        );
    }

    @Override
    public CatalogReportResponse catalogReport() {
        return catalogReportClient.getCatalogStats().data();
    }

    @Override
    public CirculationReportResponse circulationReport() {
        return circulationReportClient.getCirculationStats().data();
    }

    @Override
    public EngagementReportResponse engagementReport() {
        return engagementReportClient.getEngagementStats().data();
    }
}
