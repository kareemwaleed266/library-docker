package com.library.common.security;

import java.util.List;

public final class RoleConstants {

    private RoleConstants() {
    }

    public static final String ADMIN = "ADMIN";
    public static final String LIBRARIAN = "LIBRARIAN";
    public static final String USER = "USER";

    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    public static final String ROLE_LIBRARIAN = "ROLE_LIBRARIAN";
    public static final String ROLE_USER = "ROLE_USER";

    public static final List<String> SYSTEM_ROLES = List.of(
            ADMIN,
            LIBRARIAN,
            USER
    );
}