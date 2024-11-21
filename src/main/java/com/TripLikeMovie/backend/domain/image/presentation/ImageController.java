package com.TripLikeMovie.backend.domain.image.presentation;

import com.TripLikeMovie.backend.domain.image.service.ImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/image")
public class ImageController {

    private final ImageService imageService;

    @PostMapping("/profile")
    public void UploadProfileImage(@RequestPart MultipartFile file) {
        imageService.uploadProfileImage(file);
    }

    @DeleteMapping("/profile")
    public void deleteProfileImage() {
        imageService.deleteProfileImage();
    }

}
