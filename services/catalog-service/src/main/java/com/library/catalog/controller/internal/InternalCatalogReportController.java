package com.library.catalog.controller.internal;

import com.library.catalog.dto.internal.CatalogStatsResponse;
import com.library.catalog.repository.BookRepository;
import com.library.catalog.repository.CategoryRepository;
import com.library.common.dto.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/internal/reports/catalog")
public class InternalCatalogReportController {

    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;

    public InternalCatalogReportController(
            BookRepository bookRepository,
            CategoryRepository categoryRepository
    ) {
        this.bookRepository = bookRepository;
        this.categoryRepository = categoryRepository;
    }

    @GetMapping
    public ApiResponse<CatalogStatsResponse> getCatalogStats() {
        long totalBooks = bookRepository.count();
        long availableBooks = bookRepository.countByAvailableCopiesGreaterThan(0);
        long unavailableBooks = bookRepository.countByAvailableCopiesLessThanEqual(0);
        long totalCategories = categoryRepository.count();

        return ApiResponse.success(
                "Catalog stats retrieved successfully",
                new CatalogStatsResponse(
                        totalBooks,
                        availableBooks,
                        unavailableBooks,
                        totalCategories
                )
        );
    }
}
