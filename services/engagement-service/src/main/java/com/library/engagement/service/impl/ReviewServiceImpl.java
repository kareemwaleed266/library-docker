package com.library.engagement.service.impl;

import com.library.common.exception.BadRequestException;
import com.library.common.exception.NotFoundException;
import com.library.common.security.CurrentUser;
import com.library.engagement.dto.request.ReviewRequest;
import com.library.engagement.dto.response.ReviewResponse;
import com.library.engagement.entity.Review;
import com.library.engagement.mapper.ReviewMapper;
import com.library.engagement.repository.ReviewRepository;
import com.library.engagement.service.ReviewService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;

    public ReviewServiceImpl(
            ReviewRepository reviewRepository,
            ReviewMapper reviewMapper
    ) {
        this.reviewRepository = reviewRepository;
        this.reviewMapper = reviewMapper;
    }

    @Override
    public ReviewResponse create(CurrentUser currentUser, ReviewRequest request) {
        if (reviewRepository.existsByUserIdAndBookId(currentUser.id(), request.bookId())) {
            throw new BadRequestException("You already reviewed this book");
        }

        Review review = new Review(
                currentUser.id(),
                request.bookId(),
                request.rating(),
                normalizeComment(request.comment())
        );

        return reviewMapper.toResponse(reviewRepository.save(review));
    }

    @Override
    public ReviewResponse update(
            CurrentUser currentUser,
            UUID reviewId,
            ReviewRequest request
    ) {
        Review review = findReviewById(reviewId);

        if (!review.getUserId().equals(currentUser.id())) {
            throw new BadRequestException("You can only update your own reviews");
        }

        if (!review.getBookId().equals(request.bookId())) {
            throw new BadRequestException("Review book cannot be changed");
        }

        review.update(
                request.rating(),
                normalizeComment(request.comment())
        );

        return reviewMapper.toResponse(review);
    }

    @Override
    public void delete(CurrentUser currentUser, UUID reviewId) {
        Review review = findReviewById(reviewId);

        if (!review.getUserId().equals(currentUser.id()) && currentUser.isUser()) {
            throw new BadRequestException("You can only delete your own reviews");
        }

        reviewRepository.delete(review);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReviewResponse> getMyReviews(CurrentUser currentUser) {
        return reviewRepository.findByUserIdOrderByCreatedAtDesc(currentUser.id())
                .stream()
                .map(reviewMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReviewResponse> getBookReviews(UUID bookId) {
        return reviewRepository.findByBookIdOrderByCreatedAtDesc(bookId)
                .stream()
                .map(reviewMapper::toResponse)
                .toList();
    }

    private Review findReviewById(UUID reviewId) {
        return reviewRepository.findById(reviewId)
                .orElseThrow(() -> new NotFoundException("Review not found"));
    }

    private String normalizeComment(String comment) {
        if (comment == null || comment.isBlank()) {
            return null;
        }

        return comment.trim();
    }
}
