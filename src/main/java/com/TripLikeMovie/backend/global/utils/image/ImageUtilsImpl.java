package com.TripLikeMovie.backend.global.utils.image;

import com.TripLikeMovie.backend.global.error.exception.image.BadFileExtensionException;
import com.TripLikeMovie.backend.global.error.exception.image.FileEmptyException;
import com.TripLikeMovie.backend.global.error.exception.image.ProfileImageDeleteException;
import java.io.File;
import java.io.IOException;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
public class ImageUtilsImpl implements ImageUtils {

    public String saveImage(MultipartFile file, String path) {
        if (file.isEmpty() || file.getOriginalFilename() == null) {
            throw FileEmptyException.EXCEPTION;
        }

        String ext = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);

        if (!ext.equals("jpg") && !ext.equals("jpeg") && !ext.equals("png")) {
            throw BadFileExtensionException.EXCEPTION;
        }
        String uploadsDir = System.getProperty("user.dir") + "/uploads/" + path;
        // 디렉토리 생성
        File dir = new File(uploadsDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        String randomName = UUID.randomUUID().toString();
        String filePath = uploadsDir + randomName + "." + ext;

        try {
            file.transferTo(new File(filePath));
        } catch (IOException e) {
            throw new RuntimeException("파일 저장에 실패했습니다.", e);
        }

        return filePath;
    }

    @Override
    public void deleteImage(String imageUrl) {
        if (imageUrl != null) {
            File existingFile = new File(imageUrl);
            if (existingFile.exists()) {
                boolean isDeleted = existingFile.delete();
                if (!isDeleted) {
                    throw ProfileImageDeleteException.EXCEPTION;
                }
            }
        }
    }
}
