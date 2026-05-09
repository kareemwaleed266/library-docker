package com.library.catalog.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "books")
@EntityListeners(AuditingEntityListener.class)
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(nullable = false, length = 160)
    private String author;

    @Column(nullable = false, unique = true, length = 30)
    private String isbn;

    @Column(columnDefinition = "text")
    private String description;

    @Column(length = 1000)
    private String coverImageUrl;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Column(nullable = false)
    private int totalCopies;

    @Column(nullable = false)
    private int availableCopies;

    @Column
    private Integer publishedYear;

    @Version
    @Column(nullable = false)
    private long version;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private Instant updatedAt;

    protected Book() {
    }

    public Book(
            String title,
            String author,
            String isbn,
            String description,
            String coverImageUrl,
            Category category,
            int totalCopies,
            int availableCopies,
            Integer publishedYear
    ) {
        validateCopies(totalCopies, availableCopies);

        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.description = description;
        this.coverImageUrl = coverImageUrl;
        this.category = category;
        this.totalCopies = totalCopies;
        this.availableCopies = availableCopies;
        this.publishedYear = publishedYear;
    }

    public UUID getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getDescription() {
        return description;
    }

    public String getCoverImageUrl() {
        return coverImageUrl;
    }

    public Category getCategory() {
        return category;
    }

    public int getTotalCopies() {
        return totalCopies;
    }

    public int getAvailableCopies() {
        return availableCopies;
    }

    public Integer getPublishedYear() {
        return publishedYear;
    }

    public long getVersion() {
        return version;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void updateDetails(
            String title,
            String author,
            String isbn,
            String description,
            String coverImageUrl,
            Category category,
            Integer publishedYear
    ) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.description = description;
        this.coverImageUrl = coverImageUrl;
        this.category = category;
        this.publishedYear = publishedYear;
    }

    public void updateCoverImageUrl(String coverImageUrl) {
        this.coverImageUrl = coverImageUrl;
    }

    public void updateCopies(int totalCopies, int availableCopies) {
        validateCopies(totalCopies, availableCopies);

        this.totalCopies = totalCopies;
        this.availableCopies = availableCopies;
    }

    public boolean isAvailable() {
        return availableCopies > 0;
    }

    public void decreaseAvailableCopies() {
        if (availableCopies <= 0) {
            throw new IllegalStateException("No available copies for this book");
        }

        availableCopies--;
    }

    public void increaseAvailableCopies() {
        if (availableCopies >= totalCopies) {
            throw new IllegalStateException("Available copies cannot exceed total copies");
        }

        availableCopies++;
    }

    private void validateCopies(int totalCopies, int availableCopies) {
        if (totalCopies < 0) {
            throw new IllegalArgumentException("Total copies cannot be negative");
        }

        if (availableCopies < 0) {
            throw new IllegalArgumentException("Available copies cannot be negative");
        }

        if (availableCopies > totalCopies) {
            throw new IllegalArgumentException("Available copies cannot exceed total copies");
        }
    }
}
