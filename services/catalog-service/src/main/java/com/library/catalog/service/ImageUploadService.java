package com.library.catalog.service;

import com.cloudinary.Cloudinary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Service
public class ImageUploadService {

    private final Cloudinary cloudinary;

    public ImageUploadService(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    public String uploadBookImage(MultipartFile file) {

        try {

            Map uploadResult = cloudinary.uploader().upload(
                    file.getBytes(),
                    Map.of(
                            "folder", "library/books"
                    )
            );

            return uploadResult.get("secure_url").toString();

        } catch (Exception ex) {
            throw new RuntimeException("Failed to upload image", ex);
        }
    }
}
