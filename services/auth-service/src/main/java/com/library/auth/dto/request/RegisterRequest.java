package com.library.auth.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record RegisterRequest(

        @NotBlank(message = "Full name is required")
        @Size(min = 3, max = 120, message = "Full name must be between 3 and 120 characters")
        String fullName,

        @NotBlank(message = "Email is required")
        @Email(message = "Email format is invalid")
        @Size(max = 180, message = "Email must not exceed 180 characters")
        String email,

        @Pattern(
                regexp = "^$|^(01[0125][0-9]{8}|\\+201[0125][0-9]{8})$",
                message = "Phone must be a valid Egyptian mobile number, for example 01012345678 or +201012345678"
        )
        String phone,

        @NotBlank(message = "Password is required")
        @Size(min = 8, max = 72, message = "Password must be between 8 and 72 characters")
        String password
) {
}