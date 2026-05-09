package com.library.auth.controller;

import com.library.auth.dto.request.LoginRequest;
import com.library.auth.dto.request.RegisterRequest;
import com.library.auth.dto.response.AuthResponse;
import com.library.auth.dto.response.UserResponse;
import com.library.auth.security.UserPrincipal;
import com.library.auth.service.AuthService;
import com.library.common.dto.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<AuthResponse>> register(
            @Valid @RequestBody RegisterRequest request
    ) {
        AuthResponse response = authService.register(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success("User registered successfully", response));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(
            @Valid @RequestBody LoginRequest request
    ) {
        AuthResponse response = authService.login(request);

        return ResponseEntity.ok(
                ApiResponse.success("User logged in successfully", response)
        );
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserResponse>> getCurrentUserProfile(
            @AuthenticationPrincipal UserPrincipal currentUser
    ) {
        UserResponse response = authService.getCurrentUserProfile(currentUser.getId());

        return ResponseEntity.ok(
                ApiResponse.success("Current user profile retrieved successfully", response)
        );
    }
}