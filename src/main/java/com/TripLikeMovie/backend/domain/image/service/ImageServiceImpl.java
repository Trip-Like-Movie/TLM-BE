package com.TripLikeMovie.backend.domain.image.service;

import com.TripLikeMovie.backend.domain.image.domain.ProfileImage;
import com.TripLikeMovie.backend.domain.image.domain.repository.ProfileImageRepository;
import com.TripLikeMovie.backend.domain.member.domain.Member;
import com.TripLikeMovie.backend.global.error.exception.image.BadFileExtensionException;
import com.TripLikeMovie.backend.global.error.exception.image.FileEmptyException;
import com.TripLikeMovie.backend.global.utils.member.MemberUtils;
import java.io.File;
import java.io.IOException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ImageServiceImpl implements ImageService {

    private final MemberUtils memberUtils;
    private final ProfileImageRepository profileImageRepository;

    @Override
    public void uploadProfileImage(MultipartFile file) {
        Member member = memberUtils.getMemberFromSecurityContext();
        String filePath = saveImage(file);

        ProfileImage profileImage = new ProfileImage(filePath, member);
        profileImageRepository.save(profileImage);
        member.updateProfileImage(profileImage);
    }

    @Override
    public void deleteProfileImage() {
        Member member = memberUtils.getMemberFromSecurityContext();

    }

    private String saveImage(MultipartFile file) {
        if (file.isEmpty() || file.getOriginalFilename() == null) {
            throw FileEmptyException.EXCEPTION;
        }

        String ext = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);

        if (!ext.equals("jpg") && !ext.equals("jpeg") && !ext.equals("png")) {
            throw BadFileExtensionException.EXCEPTION;
        }
        String uploadsDir = System.getProperty("user.dir") + "/uploads/profiles/"; // 절대 경로
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
            log.info("message: {}", e.getMessage());
            throw new RuntimeException("파일 저장에 실패했습니다.", e);
        }

        return filePath;
    }
}
