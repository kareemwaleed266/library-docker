package com.library.catalog.service;

import com.library.catalog.dto.request.BookRequest;
import com.library.catalog.dto.request.BookSearchRequest;
import com.library.catalog.dto.response.BookResponse;
import com.library.catalog.dto.response.CoverUploadResponse;
import com.library.common.dto.PageResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface BookService {

    BookResponse create(BookRequest request);

    BookResponse update(UUID bookId, BookRequest request);

    void delete(UUID bookId);

    BookResponse getById(UUID bookId);

    PageResponse<BookResponse> search(BookSearchRequest request, Pageable pageable);

    BookResponse updateCopies(UUID bookId, int totalCopies, int availableCopies);

    CoverUploadResponse uploadCover(UUID bookId, MultipartFile file);

    BookResponse decreaseAvailableCopy(UUID bookId);

    BookResponse increaseAvailableCopy(UUID bookId);
}
