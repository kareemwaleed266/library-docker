package com.library.catalog.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.Year;
import java.util.UUID;

public record BookRequest(

        @NotBlank(message = "Book title is required")
        @Size(min = 2, max = 200, message = "Book title must be between 2 and 200 characters")
        String title,

        @NotBlank(message = "Book author is required")
        @Size(min = 2, max = 160, message = "Book author must be between 2 and 160 characters")
        String author,

        @NotBlank(message = "ISBN is required")
        @Size(min = 10, max = 30, message = "ISBN must be between 10 and 30 characters")
        String isbn,

        @Size(max = 3000, message = "Book description must not exceed 3000 characters")
        String description,

        @Size(max = 1000, message = "Cover image URL must not exceed 1000 characters")
        String coverImageUrl,

        @NotNull(message = "Category id is required")
        UUID categoryId,

        @Min(value = 0, message = "Total copies cannot be negative")
        int totalCopies,

        @Min(value = 0, message = "Available copies cannot be negative")
        int availableCopies,

        @Min(value = 1000, message = "Published year must be valid")
        @Max(value = 2100, message = "Published year must be valid")
        Integer publishedYear
) {
    public BookRequest {
        if (publishedYear != null && publishedYear > Year.now().getValue() + 1) {
            throw new IllegalArgumentException("Published year cannot be in the far future");
        }

        if (availableCopies > totalCopies) {
            throw new IllegalArgumentException("Available copies cannot exceed total copies");
        }
    }
}
