package com.library.catalog.dto.request;

import jakarta.validation.constraints.Min;

public record CopiesUpdateRequest(

        @Min(value = 0, message = "Total copies cannot be negative")
        int totalCopies,

        @Min(value = 0, message = "Available copies cannot be negative")
        int availableCopies
) {
    public CopiesUpdateRequest {
        if (availableCopies > totalCopies) {
            throw new IllegalArgumentException("Available copies cannot exceed total copies");
        }
    }
}