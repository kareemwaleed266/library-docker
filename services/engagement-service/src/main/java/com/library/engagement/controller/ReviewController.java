package com.library.engagement.controller;

import com.library.common.dto.ApiResponse;
import com.library.common.security.CurrentUser;
import com.library.engagement.dto.request.ReviewRequest;
import com.library.engagement.dto.response.ReviewResponse;
import com.library.engagement.service.ReviewService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<ReviewResponse> create(
            @AuthenticationPrincipal CurrentUser currentUser,
            @Valid @RequestBody ReviewRequest request
    ) {
        return ApiResponse.success(
                "Review created successfully",
                reviewService.create(currentUser, request)
        );
    }

    @PutMapping("/{reviewId}")
    public ApiResponse<ReviewResponse> update(
            @AuthenticationPrincipal CurrentUser currentUser,
            @PathVariable UUID reviewId,
            @Valid @RequestBody ReviewRequest request
    ) {
        return ApiResponse.success(
                "Review updated successfully",
                reviewService.update(currentUser, reviewId, request)
        );
    }

    @DeleteMapping("/{reviewId}")
    public ApiResponse<Void> delete(
            @AuthenticationPrincipal CurrentUser currentUser,
            @PathVariable UUID reviewId
    ) {
        reviewService.delete(currentUser, reviewId);

        return ApiResponse.success(
                "Review deleted successfully",
                null
        );
    }

    @GetMapping("/my")
    public ApiResponse<List<ReviewResponse>> getMyReviews(
            @AuthenticationPrincipal CurrentUser currentUser
    ) {
        return ApiResponse.success(
                "My reviews retrieved successfully",
                reviewService.getMyReviews(currentUser)
        );
    }

    @GetMapping("/book/{bookId}")
    public ApiResponse<List<ReviewResponse>> getBookReviews(
            @PathVariable UUID bookId
    ) {
        return ApiResponse.success(
                "Book reviews retrieved successfully",
                reviewService.getBookReviews(bookId)
        );
    }
}
