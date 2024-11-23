package com.TripLikeMovie.backend.domain.member.service;

import com.TripLikeMovie.backend.domain.member.domain.Member;
import com.TripLikeMovie.backend.domain.member.domain.repository.MemberRepository;
import com.TripLikeMovie.backend.domain.member.presentation.dto.request.ChangeNicknameRequest;
import com.TripLikeMovie.backend.domain.member.presentation.dto.request.ChangePasswordRequest;
import com.TripLikeMovie.backend.domain.member.presentation.dto.request.ChangePasswordVerifyEmailRequest;
import com.TripLikeMovie.backend.domain.member.presentation.dto.request.SendVerificationRequest;
import com.TripLikeMovie.backend.domain.member.presentation.dto.request.VerifyNicknameRequest;
import com.TripLikeMovie.backend.global.error.exception.image.NotProfileImageException;
import com.TripLikeMovie.backend.global.error.exception.image.ProfileImageDeleteException;
import com.TripLikeMovie.backend.global.error.exception.member.DuplicatedEmailException;
import com.TripLikeMovie.backend.global.error.exception.member.DuplicatedNicknameException;
import com.TripLikeMovie.backend.global.error.exception.member.EmailPasswordNotMatchException;
import com.TripLikeMovie.backend.global.error.exception.member.MemberNotFoundException;
import com.TripLikeMovie.backend.global.utils.image.ImageUtils;
import com.TripLikeMovie.backend.global.utils.member.MemberUtils;
import java.io.File;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final Set<String> nicknameSet = ConcurrentHashMap.newKeySet();
    private final PasswordEncoder encoder;
    private final MemberUtils memberUtils;
    private final ImageUtils imageUtils;

    @Override
    @Transactional(readOnly = true)
    public void validateEmail(SendVerificationRequest sendVerificationRequest) {
        memberRepository.findByEmail(sendVerificationRequest.getEmail()).ifPresent(member -> {
            throw DuplicatedEmailException.EXCEPTION;
        });
    }

    @Override
    @Transactional(readOnly = true)
    public void validateNickname(VerifyNicknameRequest verifyNicknameRequest) {
        memberRepository.findByNickname(verifyNicknameRequest.getNickname()).ifPresent(member -> {
            throw DuplicatedNicknameException.EXCEPTION;
        });
        if (nicknameSet.contains(verifyNicknameRequest.getNickname())) {
            throw DuplicatedNicknameException.EXCEPTION;
        }
        nicknameSet.add(verifyNicknameRequest.getNickname());
    }

    @Override
    @Transactional(readOnly = true)
    public void signUpSuccess(String nickname) {
        nicknameSet.remove(nickname);
    }

    @Override
    @Transactional(readOnly = true)
    public void changePasswordVerifyEmail(ChangePasswordVerifyEmailRequest changePasswordVerifyEmail) {
        Member member = memberRepository.findByEmail(changePasswordVerifyEmail.getEmail())
            .orElseThrow(() -> MemberNotFoundException.EXCEPTION);

        if (!member.getNickname().equals(changePasswordVerifyEmail.getNickname())) {
            throw EmailPasswordNotMatchException.EXCEPTION;
        }
    }

    @Override
    public void changePassword(ChangePasswordRequest changePasswordRequest) {
        Member member = memberRepository.findByEmail(changePasswordRequest.getEmail())
            .orElseThrow(() -> MemberNotFoundException.EXCEPTION);

        String encodedPassword = encoder.encode(changePasswordRequest.getPassword());
        member.changePassword(encodedPassword);
        memberRepository.save(member);
    }

    @Override
    public void changeNickname(ChangeNicknameRequest changeNicknameRequest) {
        memberRepository.findByNickname(changeNicknameRequest.getNickname()).ifPresent(member -> {
            throw DuplicatedNicknameException.EXCEPTION;
        });
        Member member = memberUtils.getMemberFromSecurityContext();

        member.changeNickname(changeNicknameRequest.getNickname());
        memberRepository.save(member);
    }


    @Override
    public void deleteProfileImage() {
        Member member = memberUtils.getMemberFromSecurityContext();

        String imagePath = member.getImageUrl();

        if (imagePath == null) {
            throw NotProfileImageException.EXCEPTION;
        }
        // 파일 삭제
        File file = new File(imagePath);
        if (file.exists()) {
            boolean isDeleted = file.delete();
            if (!isDeleted) {
                throw ProfileImageDeleteException.EXCEPTION;
            }
        }
        member.updateProfileImage(null); // Member에서 프로필 이미지 제거
        memberRepository.flush();

    }

    @Override
    public void updateProfileImage(MultipartFile file) {
        Member member = memberUtils.getMemberFromSecurityContext();

        String imagePath = member.getImageUrl();

        // 기존 프로필 이미지가 있으면 파일 삭제 및 엔티티 삭제
        imageUtils.deleteImage(imagePath);

        // 새로운 이미지 저장
        String filePath = imageUtils.saveImage(file, "profiles/");

        // Member 엔티티 업데이트
        member.updateProfileImage(filePath);
        memberRepository.flush();

    }

    @Override
    public Member findById(Integer memberId) {
        return memberRepository.findById(memberId).orElseThrow(() -> MemberNotFoundException.EXCEPTION);
    }
}