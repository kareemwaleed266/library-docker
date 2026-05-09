package com.library.auth.dto.response;

import com.library.auth.enums.RoleName;

import java.time.Instant;
import java.util.UUID;

public record UserResponse(
        UUID id,
        String fullName,
        String email,
        String phone,
        RoleName role,
        boolean active,
        Instant createdAt,
        Instant updatedAt
) {
}