package com.library.report.client;

import com.library.common.dto.ApiResponse;
import com.library.report.dto.response.CatalogReportResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "catalog-service")
public interface CatalogReportClient {

    @GetMapping("/internal/reports/catalog")
    ApiResponse<CatalogReportResponse> getCatalogStats();
}
