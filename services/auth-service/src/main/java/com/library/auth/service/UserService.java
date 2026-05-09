package com.library.auth.service;

import com.library.auth.dto.request.UpdateRoleRequest;
import com.library.auth.dto.response.UserResponse;
import com.library.common.dto.PageResponse;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface UserService {

    PageResponse<UserResponse> getUsers(Pageable pageable);

    UserResponse getUserById(UUID userId);

    UserResponse updateUserRole(UUID userId, UpdateRoleRequest request);

    UserResponse activateUser(UUID userId);

    UserResponse deactivateUser(UUID userId);
}