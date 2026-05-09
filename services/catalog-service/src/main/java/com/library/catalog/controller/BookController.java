package com.library.catalog.controller;

import com.library.catalog.dto.request.BookRequest;
import com.library.catalog.dto.request.BookSearchRequest;
import com.library.catalog.dto.request.CopiesUpdateRequest;
import com.library.catalog.dto.response.BookResponse;
import com.library.catalog.dto.response.CoverUploadResponse;
import com.library.catalog.service.BookService;
import com.library.common.dto.ApiResponse;
import com.library.common.dto.PageResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<BookResponse> create(
            @Valid @RequestBody BookRequest request
    ) {
        return ApiResponse.success(
                "Book created successfully",
                bookService.create(request)
        );
    }

    @PutMapping("/{bookId}")
    public ApiResponse<BookResponse> update(
            @PathVariable UUID bookId,
            @Valid @RequestBody BookRequest request
    ) {
        return ApiResponse.success(
                "Book updated successfully",
                bookService.update(bookId, request)
        );
    }

    @PatchMapping("/{bookId}/copies")
    public ApiResponse<BookResponse> updateCopies(
            @PathVariable UUID bookId,
            @Valid @RequestBody CopiesUpdateRequest request
    ) {
        return ApiResponse.success(
                "Book copies updated successfully",
                bookService.updateCopies(
                        bookId,
                        request.totalCopies(),
                        request.availableCopies()
                )
        );
    }

    @PostMapping("/{bookId}/cover")
    public ApiResponse<CoverUploadResponse> uploadCover(
            @PathVariable UUID bookId,
            @RequestParam("file") MultipartFile file
    ) {
        return ApiResponse.success(
                "Book cover uploaded successfully",
                bookService.uploadCover(bookId, file)
        );
    }

    @DeleteMapping("/{bookId}")
    public ApiResponse<Void> delete(
            @PathVariable UUID bookId
    ) {
        bookService.delete(bookId);

        return ApiResponse.success(
                "Book deleted successfully",
                null
        );
    }

    @GetMapping("/{bookId}")
    public ApiResponse<BookResponse> getById(
            @PathVariable UUID bookId
    ) {
        return ApiResponse.success(
                "Book retrieved successfully",
                bookService.getById(bookId)
        );
    }

    @GetMapping
    public ApiResponse<PageResponse<BookResponse>> search(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String author,
            @RequestParam(required = false) String isbn,
            @RequestParam(required = false) UUID categoryId,
            @RequestParam(required = false) Boolean availableOnly,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {

        BookSearchRequest request = new BookSearchRequest(
                title,
                author,
                isbn,
                categoryId,
                availableOnly
        );

        Pageable pageable = PageRequest.of(page, size);

        return ApiResponse.success(
                "Books retrieved successfully",
                bookService.search(request, pageable)
        );
    }
}
