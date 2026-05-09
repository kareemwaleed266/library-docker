package com.library.engagement.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(
        name = "favorites",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_favorites_user_book", columnNames = {"user_id", "book_id"})
        }
)
@EntityListeners(AuditingEntityListener.class)
public class Favorite {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private UUID userId;

    @Column(nullable = false)
    private UUID bookId;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    protected Favorite() {
    }

    public Favorite(UUID userId, UUID bookId) {
        this.userId = userId;
        this.bookId = bookId;
    }

    public UUID getId() {
        return id;
    }

    public UUID getUserId() {
        return userId;
    }

    public UUID getBookId() {
        return bookId;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }
}
