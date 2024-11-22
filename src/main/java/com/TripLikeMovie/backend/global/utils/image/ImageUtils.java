package com.TripLikeMovie.backend.global.utils.image;

import org.springframework.web.multipart.MultipartFile;

public interface ImageUtils {
    String saveImage(MultipartFile file, String path);
}
