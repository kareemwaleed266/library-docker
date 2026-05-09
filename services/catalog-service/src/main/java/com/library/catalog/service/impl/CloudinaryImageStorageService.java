package com.library.catalog.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.library.catalog.config.CloudinaryProperties;
import com.library.catalog.service.ImageStorageService;
import com.library.common.exception.BadRequestException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class CloudinaryImageStorageService implements ImageStorageService {

    private static final long MAX_IMAGE_SIZE_BYTES = 5 * 1024 * 1024;

    private final Cloudinary cloudinary;
    private final CloudinaryProperties properties;

    public CloudinaryImageStorageService(
            Cloudinary cloudinary,
            CloudinaryProperties properties
    ) {
        this.cloudinary = cloudinary;
        this.properties = properties;
    }

    @Override
    public String uploadBookCover(MultipartFile file) {
        validateImage(file);

        try {
            Map<?, ?> uploadResult = cloudinary.uploader().upload(
                    file.getBytes(),
                    ObjectUtils.asMap(
                            "folder", properties.folder(),
                            "resource_type", "image"
                    )
            );

            Object secureUrl = uploadResult.get("secure_url");

            if (secureUrl == null) {
                throw new BadRequestException("Cloud image upload failed");
            }

            return secureUrl.toString();
        } catch (IOException exception) {
            throw new BadRequestException("Could not read uploaded image");
        }
    }

    private void validateImage(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new BadRequestException("Image file is required");
        }

        if (file.getSize() > MAX_IMAGE_SIZE_BYTES) {
            throw new BadRequestException("Image size must not exceed 5 MB");
        }

        String contentType = file.getContentType();

        if (contentType == null || !contentType.startsWith("image/")) {
            throw new BadRequestException("Only image files are allowed");
        }
    }
}
