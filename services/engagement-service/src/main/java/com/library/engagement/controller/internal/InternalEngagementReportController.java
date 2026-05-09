package com.library.engagement.controller.internal;

import com.library.common.dto.ApiResponse;
import com.library.engagement.dto.internal.EngagementStatsResponse;
import com.library.engagement.repository.FavoriteRepository;
import com.library.engagement.repository.ReviewRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/internal/reports/engagement")
public class InternalEngagementReportController {

    private final FavoriteRepository favoriteRepository;
    private final ReviewRepository reviewRepository;

    public InternalEngagementReportController(
            FavoriteRepository favoriteRepository,
            ReviewRepository reviewRepository
    ) {
        this.favoriteRepository = favoriteRepository;
        this.reviewRepository = reviewRepository;
    }

    @GetMapping
    public ApiResponse<EngagementStatsResponse> getEngagementStats() {
        return ApiResponse.success(
                "Engagement stats retrieved successfully",
                new EngagementStatsResponse(
                        favoriteRepository.count(),
                        reviewRepository.count(),
                        reviewRepository.averageRating()
                )
        );
    }
}
