package com.TripLikeMovie.backend.domain.member.service;

import com.TripLikeMovie.backend.domain.member.domain.Member;
import com.TripLikeMovie.backend.domain.member.presentation.dto.request.ChangeNicknameRequest;
import com.TripLikeMovie.backend.domain.member.presentation.dto.request.ChangePasswordRequest;
import com.TripLikeMovie.backend.domain.member.presentation.dto.request.ChangePasswordVerifyEmailRequest;
import com.TripLikeMovie.backend.domain.member.presentation.dto.request.SendVerificationRequest;
import com.TripLikeMovie.backend.domain.member.presentation.dto.request.VerifyNicknameRequest;
import com.TripLikeMovie.backend.domain.post.presentation.dto.response.MemberAllPost;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface MemberService {

    void validateEmail(SendVerificationRequest sendVerificationRequest);

    void validateNickname(VerifyNicknameRequest verifyNicknameRequest);

    void signUpSuccess(String nickname);

    void changePasswordVerifyEmail(ChangePasswordVerifyEmailRequest changePasswordVerifyEmail);

    void changePassword(ChangePasswordRequest changePasswordRequest);

    void changeNickname(ChangeNicknameRequest changeNicknameRequest);

    void deleteProfileImage();

    void updateProfileImage(MultipartFile file);

    Member findById(Integer memberId);

    List<MemberAllPost> getAllPosts(Integer memberId);
}
