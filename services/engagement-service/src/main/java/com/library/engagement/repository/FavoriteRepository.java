package com.library.engagement.repository;

import com.library.engagement.entity.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FavoriteRepository extends JpaRepository<Favorite, UUID> {

    List<Favorite> findByUserIdOrderByCreatedAtDesc(UUID userId);

    boolean existsByUserIdAndBookId(UUID userId, UUID bookId);

    Optional<Favorite> findByUserIdAndBookId(UUID userId, UUID bookId);

    void deleteByUserIdAndBookId(UUID userId, UUID bookId);
}
