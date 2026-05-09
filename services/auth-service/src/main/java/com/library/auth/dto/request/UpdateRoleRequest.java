package com.library.auth.dto.request;

import com.library.auth.enums.RoleName;
import jakarta.validation.constraints.NotNull;

public record UpdateRoleRequest(

        @NotNull(message = "Role is required")
        RoleName role
) {
}