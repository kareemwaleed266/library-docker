package com.library.engagement.controller;

import com.library.common.dto.ApiResponse;
import com.library.common.security.CurrentUser;
import com.library.engagement.dto.request.FavoriteRequest;
import com.library.engagement.dto.response.FavoriteResponse;
import com.library.engagement.service.FavoriteService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/favorites")
public class FavoriteController {

    private final FavoriteService favoriteService;

    public FavoriteController(FavoriteService favoriteService) {
        this.favoriteService = favoriteService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<FavoriteResponse> add(
            @AuthenticationPrincipal CurrentUser currentUser,
            @Valid @RequestBody FavoriteRequest request
    ) {
        return ApiResponse.success(
                "Favorite added successfully",
                favoriteService.add(currentUser, request)
        );
    }

    @GetMapping("/my")
    public ApiResponse<List<FavoriteResponse>> getMyFavorites(
            @AuthenticationPrincipal CurrentUser currentUser
    ) {
        return ApiResponse.success(
                "My favorites retrieved successfully",
                favoriteService.getMyFavorites(currentUser)
        );
    }

    @DeleteMapping("/{bookId}")
    public ApiResponse<Void> remove(
            @AuthenticationPrincipal CurrentUser currentUser,
            @PathVariable UUID bookId
    ) {
        favoriteService.remove(currentUser, bookId);

        return ApiResponse.success(
                "Favorite removed successfully",
                null
        );
    }
}
