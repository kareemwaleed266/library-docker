package com.library.catalog.service;

import org.springframework.web.multipart.MultipartFile;

public interface ImageStorageService {

    String uploadBookCover(MultipartFile file);
}
