package com.library.catalog.service;

import com.library.catalog.dto.request.CategoryRequest;
import com.library.catalog.dto.response.CategoryResponse;

import java.util.List;
import java.util.UUID;

public interface CategoryService {

    CategoryResponse create(CategoryRequest request);

    CategoryResponse update(UUID categoryId, CategoryRequest request);

    void delete(UUID categoryId);

    CategoryResponse getById(UUID categoryId);

    List<CategoryResponse> getAll();
}