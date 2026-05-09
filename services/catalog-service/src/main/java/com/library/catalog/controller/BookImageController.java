package com.library.catalog.controller;

import com.library.catalog.service.ImageUploadService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/api/books/images")
public class BookImageController {

    private final ImageUploadService imageUploadService;

    public BookImageController(ImageUploadService imageUploadService) {
        this.imageUploadService = imageUploadService;
    }

    @PostMapping(
            value = "/upload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public Map<String, String> uploadBookImage(
            @RequestPart("image") MultipartFile image
    ) {

        String imageUrl = imageUploadService.uploadBookImage(image);

        return Map.of(
                "imageUrl",
                imageUrl
        );
    }
}
