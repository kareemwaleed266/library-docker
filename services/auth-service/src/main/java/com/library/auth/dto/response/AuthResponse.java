package com.library.auth.dto.response;

public record AuthResponse(
        String accessToken,
        String tokenType,
        long expiresInMinutes,
        UserResponse user
) {
    public static AuthResponse bearer(
            String accessToken,
            long expiresInMinutes,
            UserResponse user
    ) {
        return new AuthResponse(
                accessToken,
                "Bearer",
                expiresInMinutes,
                user
        );
    }
}