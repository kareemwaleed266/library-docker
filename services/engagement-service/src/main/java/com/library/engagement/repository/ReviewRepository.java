package com.library.engagement.repository;

import com.library.engagement.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ReviewRepository extends JpaRepository<Review, UUID> {

    List<Review> findByBookIdOrderByCreatedAtDesc(UUID bookId);

    List<Review> findByUserIdOrderByCreatedAtDesc(UUID userId);

    Optional<Review> findByUserIdAndBookId(UUID userId, UUID bookId);

    boolean existsByUserIdAndBookId(UUID userId, UUID bookId);

    @Query("select coalesce(avg(r.rating), 0) from Review r")
    double averageRating();
}
