package com.library.catalog.service.impl;

import com.library.catalog.dto.request.CategoryRequest;
import com.library.catalog.dto.response.CategoryResponse;
import com.library.catalog.entity.Category;
import com.library.catalog.mapper.CategoryMapper;
import com.library.catalog.repository.CategoryRepository;
import com.library.catalog.service.CategoryService;
import com.library.common.exception.ConflictException;
import com.library.common.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryServiceImpl(
            CategoryRepository categoryRepository,
            CategoryMapper categoryMapper
    ) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    @Override
    public CategoryResponse create(CategoryRequest request) {
        String name = normalizeName(request.name());

        if (categoryRepository.existsByNameIgnoreCase(name)) {
            throw new ConflictException("Category name already exists");
        }

        Category category = new Category(
                name,
                normalizeDescription(request.description())
        );

        Category savedCategory = categoryRepository.save(category);

        return categoryMapper.toResponse(savedCategory);
    }

    @Override
    public CategoryResponse update(UUID categoryId, CategoryRequest request) {
        Category category = findCategoryById(categoryId);

        String name = normalizeName(request.name());

        if (categoryRepository.existsByNameIgnoreCaseAndIdNot(name, categoryId)) {
            throw new ConflictException("Category name already exists");
        }

        category.updateName(name);
        category.updateDescription(normalizeDescription(request.description()));

        return categoryMapper.toResponse(category);
    }

    @Override
    public void delete(UUID categoryId) {
        Category category = findCategoryById(categoryId);
        categoryRepository.delete(category);
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryResponse getById(UUID categoryId) {
        return categoryMapper.toResponse(findCategoryById(categoryId));
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryResponse> getAll() {
        return categoryRepository.findAll()
                .stream()
                .map(categoryMapper::toResponse)
                .toList();
    }

    private Category findCategoryById(UUID categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NotFoundException("Category not found"));
    }

    private String normalizeName(String name) {
        return name.trim();
    }

    private String normalizeDescription(String description) {
        if (description == null || description.isBlank()) {
            return null;
        }

        return description.trim();
    }
}