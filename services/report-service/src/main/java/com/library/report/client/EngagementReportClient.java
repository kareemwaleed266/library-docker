package com.library.report.client;

import com.library.common.dto.ApiResponse;
import com.library.report.dto.response.EngagementReportResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "engagement-service")
public interface EngagementReportClient {

    @GetMapping("/internal/reports/engagement")
    ApiResponse<EngagementReportResponse> getEngagementStats();
}
