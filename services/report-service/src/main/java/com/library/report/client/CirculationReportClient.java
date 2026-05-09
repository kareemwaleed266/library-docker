package com.library.report.client;

import com.library.common.dto.ApiResponse;
import com.library.report.dto.response.CirculationReportResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "circulation-service")
public interface CirculationReportClient {

    @GetMapping("/internal/reports/circulation")
    ApiResponse<CirculationReportResponse> getCirculationStats();
}
