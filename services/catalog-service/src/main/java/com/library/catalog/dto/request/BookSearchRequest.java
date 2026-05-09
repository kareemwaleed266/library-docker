package com.library.catalog.dto.request;

import java.util.UUID;

public record BookSearchRequest(

        String title,

        String author,

        String isbn,

        UUID categoryId,

        Boolean availableOnly
) {
}