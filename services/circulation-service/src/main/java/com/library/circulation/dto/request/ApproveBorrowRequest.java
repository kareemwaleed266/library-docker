package com.library.circulation.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record ApproveBorrowRequest(

        @NotNull(message = "Borrow duration days is required")
        @Min(value = 1, message = "Borrow duration must be at least 1 day")
        Integer borrowDurationDays
) {
}
