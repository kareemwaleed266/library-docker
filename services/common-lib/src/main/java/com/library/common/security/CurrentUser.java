package com.library.common.security;

import java.util.UUID;

public record CurrentUser(
        UUID id,
        String email,
        String role
) {
    public boolean hasRole(String requiredRole) {
        return role != null && role.equals(requiredRole);
    }

    public boolean isAdmin() {
        return hasRole(RoleConstants.ADMIN);
    }

    public boolean isLibrarian() {
        return hasRole(RoleConstants.LIBRARIAN);
    }

    public boolean isUser() {
        return hasRole(RoleConstants.USER);
    }
}