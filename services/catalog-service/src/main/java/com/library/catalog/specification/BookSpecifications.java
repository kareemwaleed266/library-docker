package com.library.catalog.specification;

import com.library.catalog.dto.request.BookSearchRequest;
import com.library.catalog.entity.Book;
import org.springframework.data.jpa.domain.Specification;

public final class BookSpecifications {

    private BookSpecifications() {
    }

    public static Specification<Book> withFilters(BookSearchRequest request) {
        return Specification
                .where(titleContains(request.title()))
                .and(authorContains(request.author()))
                .and(isbnEquals(request.isbn()))
                .and(categoryEquals(request.categoryId()))
                .and(availableOnly(request.availableOnly()));
    }

    private static Specification<Book> titleContains(String title) {
        return (root, query, criteriaBuilder) -> {
            if (title == null || title.isBlank()) {
                return null;
            }

            return criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("title")),
                    "%" + title.trim().toLowerCase() + "%"
            );
        };
    }

    private static Specification<Book> authorContains(String author) {
        return (root, query, criteriaBuilder) -> {
            if (author == null || author.isBlank()) {
                return null;
            }

            return criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("author")),
                    "%" + author.trim().toLowerCase() + "%"
            );
        };
    }

    private static Specification<Book> isbnEquals(String isbn) {
        return (root, query, criteriaBuilder) -> {
            if (isbn == null || isbn.isBlank()) {
                return null;
            }

            return criteriaBuilder.equal(
                    criteriaBuilder.lower(root.get("isbn")),
                    isbn.trim().toLowerCase()
            );
        };
    }

    private static Specification<Book> categoryEquals(java.util.UUID categoryId) {
        return (root, query, criteriaBuilder) -> {
            if (categoryId == null) {
                return null;
            }

            return criteriaBuilder.equal(
                    root.get("category").get("id"),
                    categoryId
            );
        };
    }

    private static Specification<Book> availableOnly(Boolean availableOnly) {
        return (root, query, criteriaBuilder) -> {
            if (availableOnly == null || !availableOnly) {
                return null;
            }

            return criteriaBuilder.greaterThan(
                    root.get("availableCopies"),
                    0
            );
        };
    }
}