package com.TripLikeMovie.backend.domain.image.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface ImageService {

    void uploadProfileImage(MultipartFile file);

    void deleteProfileImage();

}
