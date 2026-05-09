package com.library.auth.service;

import com.library.auth.dto.request.LoginRequest;
import com.library.auth.dto.request.RegisterRequest;
import com.library.auth.dto.response.AuthResponse;
import com.library.auth.dto.response.UserResponse;

import java.util.UUID;

public interface AuthService {

    AuthResponse register(RegisterRequest request);

    AuthResponse login(LoginRequest request);

    UserResponse getCurrentUserProfile(UUID userId);
}