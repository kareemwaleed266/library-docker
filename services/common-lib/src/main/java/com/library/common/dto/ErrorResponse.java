package com.library.common.dto;

import java.time.Instant;
import java.util.List;

public record ErrorResponse(
        boolean success,
        int status,
        String error,
        String message,
        String path,
        List<String> validationErrors,
        Instant timestamp
) {
    public static ErrorResponse of(
            int status,
            String error,
            String message,
            String path
    ) {
        return new ErrorResponse(
                false,
                status,
                error,
                message,
                path,
                List.of(),
                Instant.now()
        );
    }

    public static ErrorResponse validation(
            int status,
            String error,
            String message,
            String path,
            List<String> validationErrors
    ) {
        return new ErrorResponse(
                false,
                status,
                error,
                message,
                path,
                validationErrors,
                Instant.now()
        );
    }
}