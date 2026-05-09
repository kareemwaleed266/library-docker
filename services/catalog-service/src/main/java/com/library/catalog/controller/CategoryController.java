package com.library.catalog.controller;

import com.library.catalog.dto.request.CategoryRequest;
import com.library.catalog.dto.response.CategoryResponse;
import com.library.catalog.service.CategoryService;
import com.library.common.dto.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<CategoryResponse> create(
            @Valid @RequestBody CategoryRequest request
    ) {
        return ApiResponse.success(
                "Category created successfully",
                categoryService.create(request)
        );
    }

    @PutMapping("/{categoryId}")
    public ApiResponse<CategoryResponse> update(
            @PathVariable UUID categoryId,
            @Valid @RequestBody CategoryRequest request
    ) {
        return ApiResponse.success(
                "Category updated successfully",
                categoryService.update(categoryId, request)
        );
    }

    @DeleteMapping("/{categoryId}")
    public ApiResponse<Void> delete(
            @PathVariable UUID categoryId
    ) {
        categoryService.delete(categoryId);

        return ApiResponse.success(
                "Category deleted successfully",
                null
        );
    }

    @GetMapping("/{categoryId}")
    public ApiResponse<CategoryResponse> getById(
            @PathVariable UUID categoryId
    ) {
        return ApiResponse.success(
                "Category retrieved successfully",
                categoryService.getById(categoryId)
        );
    }

    @GetMapping
    public ApiResponse<List<CategoryResponse>> getAll() {
        return ApiResponse.success(
                "Categories retrieved successfully",
                categoryService.getAll()
        );
    }
}