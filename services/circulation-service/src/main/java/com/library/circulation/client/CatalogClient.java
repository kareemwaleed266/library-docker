package com.library.circulation.client;

import com.library.common.dto.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(
        name = "catalog-service",
        url = "${services.catalog.url:https://library-catalog-service.onrender.com}"
)
public interface CatalogClient {

    @PostMapping("/internal/books/{bookId}/decrease-copy")
    ApiResponse<Object> decreaseAvailableCopy(@PathVariable UUID bookId);

    @PostMapping("/internal/books/{bookId}/increase-copy")
    ApiResponse<Object> increaseAvailableCopy(@PathVariable UUID bookId);
}