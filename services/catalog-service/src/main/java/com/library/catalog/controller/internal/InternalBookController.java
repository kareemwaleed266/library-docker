package com.library.catalog.controller.internal;

import com.library.catalog.dto.response.BookResponse;
import com.library.catalog.service.BookService;
import com.library.common.dto.ApiResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/internal/books")
public class InternalBookController {

    private final BookService bookService;

    public InternalBookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping("/{bookId}/decrease-copy")
    public ApiResponse<BookResponse> decreaseAvailableCopy(
            @PathVariable UUID bookId
    ) {
        return ApiResponse.success(
                "Book available copy decreased successfully",
                bookService.decreaseAvailableCopy(bookId)
        );
    }

    @PostMapping("/{bookId}/increase-copy")
    public ApiResponse<BookResponse> increaseAvailableCopy(
            @PathVariable UUID bookId
    ) {
        return ApiResponse.success(
                "Book available copy increased successfully",
                bookService.increaseAvailableCopy(bookId)
        );
    }
}

