package com.library.auth.controller;

import com.library.auth.dto.request.UpdateRoleRequest;
import com.library.auth.dto.response.UserResponse;
import com.library.auth.service.UserService;
import com.library.common.dto.ApiResponse;
import com.library.common.dto.PageResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@PreAuthorize("hasRole('ADMIN')")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<UserResponse>>> getUsers(
            @PageableDefault(size = 10, sort = "createdAt") Pageable pageable
    ) {
        PageResponse<UserResponse> response = userService.getUsers(pageable);

        return ResponseEntity.ok(
                ApiResponse.success("Users retrieved successfully", response)
        );
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<UserResponse>> getUserById(
            @PathVariable UUID userId
    ) {
        UserResponse response = userService.getUserById(userId);

        return ResponseEntity.ok(
                ApiResponse.success("User retrieved successfully", response)
        );
    }

    @PatchMapping("/{userId}/role")
    public ResponseEntity<ApiResponse<UserResponse>> updateUserRole(
            @PathVariable UUID userId,
            @Valid @RequestBody UpdateRoleRequest request
    ) {
        UserResponse response = userService.updateUserRole(userId, request);

        return ResponseEntity.ok(
                ApiResponse.success("User role updated successfully", response)
        );
    }

    @PatchMapping("/{userId}/activate")
    public ResponseEntity<ApiResponse<UserResponse>> activateUser(
            @PathVariable UUID userId
    ) {
        UserResponse response = userService.activateUser(userId);

        return ResponseEntity.ok(
                ApiResponse.success("User activated successfully", response)
        );
    }

    @PatchMapping("/{userId}/deactivate")
    public ResponseEntity<ApiResponse<UserResponse>> deactivateUser(
            @PathVariable UUID userId
    ) {
        UserResponse response = userService.deactivateUser(userId);

        return ResponseEntity.ok(
                ApiResponse.success("User deactivated successfully", response)
        );
    }
}