package com.library.catalog.service.impl;

import com.library.catalog.dto.request.BookRequest;
import com.library.catalog.dto.request.BookSearchRequest;
import com.library.catalog.dto.response.BookResponse;
import com.library.catalog.dto.response.CoverUploadResponse;
import com.library.catalog.entity.Book;
import com.library.catalog.entity.Category;
import com.library.catalog.mapper.BookMapper;
import com.library.catalog.repository.BookRepository;
import com.library.catalog.repository.CategoryRepository;
import com.library.catalog.service.BookService;
import com.library.catalog.service.ImageStorageService;
import com.library.catalog.specification.BookSpecifications;
import com.library.common.dto.PageResponse;
import com.library.common.exception.ConflictException;
import com.library.common.exception.NotFoundException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
@Transactional
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;
    private final BookMapper bookMapper;
    private final ImageStorageService imageStorageService;

    public BookServiceImpl(
            BookRepository bookRepository,
            CategoryRepository categoryRepository,
            BookMapper bookMapper,
            ImageStorageService imageStorageService
    ) {
        this.bookRepository = bookRepository;
        this.categoryRepository = categoryRepository;
        this.bookMapper = bookMapper;
        this.imageStorageService = imageStorageService;
    }

    @Override
    public BookResponse create(BookRequest request) {
        validateIsbnUniqueness(request.isbn());

        Category category = findCategoryById(request.categoryId());

        Book book = new Book(
                normalize(request.title()),
                normalize(request.author()),
                normalize(request.isbn()),
                normalizeNullable(request.description()),
                normalizeNullable(request.coverImageUrl()),
                category,
                request.totalCopies(),
                request.availableCopies(),
                request.publishedYear()
        );

        Book savedBook = bookRepository.save(book);

        return bookMapper.toResponse(savedBook);
    }

    @Override
    public BookResponse update(UUID bookId, BookRequest request) {
        Book book = findBookById(bookId);

        validateIsbnUniquenessForUpdate(request.isbn(), bookId);

        Category category = findCategoryById(request.categoryId());

        book.updateDetails(
                normalize(request.title()),
                normalize(request.author()),
                normalize(request.isbn()),
                normalizeNullable(request.description()),
                normalizeNullable(request.coverImageUrl()),
                category,
                request.publishedYear()
        );

        book.updateCopies(
                request.totalCopies(),
                request.availableCopies()
        );

        return bookMapper.toResponse(book);
    }

    @Override
    public void delete(UUID bookId) {
        Book book = findBookById(bookId);
        bookRepository.delete(book);
    }

    @Override
    @Transactional(readOnly = true)
    public BookResponse getById(UUID bookId) {
        return bookMapper.toResponse(findBookById(bookId));
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<BookResponse> search(
            BookSearchRequest request,
            Pageable pageable
    ) {
        return PageResponse.from(
                bookRepository.findAll(
                                BookSpecifications.withFilters(request),
                                pageable
                        )
                        .map(bookMapper::toResponse)
        );
    }

    @Override
    public BookResponse updateCopies(
            UUID bookId,
            int totalCopies,
            int availableCopies
    ) {
        Book book = findBookById(bookId);

        book.updateCopies(totalCopies, availableCopies);

        return bookMapper.toResponse(book);
    }

    @Override
    public CoverUploadResponse uploadCover(UUID bookId, MultipartFile file) {
        Book book = findBookById(bookId);

        String coverImageUrl = imageStorageService.uploadBookCover(file);

        book.updateCoverImageUrl(coverImageUrl);

        return new CoverUploadResponse(coverImageUrl);
    }

    @Override
    public BookResponse decreaseAvailableCopy(UUID bookId) {
        Book book = findBookById(bookId);

        book.decreaseAvailableCopies();

        return bookMapper.toResponse(book);
    }

    @Override
    public BookResponse increaseAvailableCopy(UUID bookId) {
        Book book = findBookById(bookId);

        book.increaseAvailableCopies();

        return bookMapper.toResponse(book);
    }

    private Book findBookById(UUID bookId) {
        return bookRepository.findById(bookId)
                .orElseThrow(() -> new NotFoundException("Book not found"));
    }

    private Category findCategoryById(UUID categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NotFoundException("Category not found"));
    }

    private void validateIsbnUniqueness(String isbn) {
        if (bookRepository.existsByIsbn(normalize(isbn))) {
            throw new ConflictException("ISBN already exists");
        }
    }

    private void validateIsbnUniquenessForUpdate(
            String isbn,
            UUID bookId
    ) {
        if (bookRepository.existsByIsbnAndIdNot(normalize(isbn), bookId)) {
            throw new ConflictException("ISBN already exists");
        }
    }

    private String normalize(String value) {
        return value.trim();
    }

    private String normalizeNullable(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }

        return value.trim();
    }
}
