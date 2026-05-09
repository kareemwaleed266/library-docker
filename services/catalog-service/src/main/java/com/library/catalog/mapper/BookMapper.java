package com.library.catalog.mapper;

import com.library.catalog.dto.response.BookResponse;
import com.library.catalog.entity.Book;
import org.springframework.stereotype.Component;

@Component
public class BookMapper {

    public BookResponse toResponse(Book book) {
        return new BookResponse(
                book.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.getIsbn(),
                book.getDescription(),
                book.getCoverImageUrl(),
                book.getCategory().getId(),
                book.getCategory().getName(),
                book.getTotalCopies(),
                book.getAvailableCopies(),
                book.isAvailable(),
                book.getPublishedYear(),
                book.getCreatedAt(),
                book.getUpdatedAt()
        );
    }
}
