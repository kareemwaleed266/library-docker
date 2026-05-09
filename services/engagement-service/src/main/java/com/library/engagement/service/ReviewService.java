package com.library.engagement.service;

import com.library.common.security.CurrentUser;
import com.library.engagement.dto.request.ReviewRequest;
import com.library.engagement.dto.response.ReviewResponse;

import java.util.List;
import java.util.UUID;

public interface ReviewService {

    ReviewResponse create(CurrentUser currentUser, ReviewRequest request);

    ReviewResponse update(CurrentUser currentUser, UUID reviewId, ReviewRequest request);

    void delete(CurrentUser currentUser, UUID reviewId);

    List<ReviewResponse> getMyReviews(CurrentUser currentUser);

    List<ReviewResponse> getBookReviews(UUID bookId);
}
