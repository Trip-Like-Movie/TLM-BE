package com.TripLikeMovie.backend.global.utils.image;

import com.TripLikeMovie.backend.global.error.exception.image.BadFileExtensionException;
import com.TripLikeMovie.backend.global.error.exception.image.FileEmptyException;
import java.io.File;
import java.io.IOException;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
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
        String filePath = uploadsDir +  randomName + "." + ext;

        try {
            file.transferTo(new File(filePath));
        } catch (IOException e) {
            throw new RuntimeException("파일 저장에 실패했습니다.", e);
        }

        return filePath;
    }
}
