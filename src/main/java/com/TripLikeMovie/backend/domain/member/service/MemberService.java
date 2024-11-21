package com.TripLikeMovie.backend.domain.member.service;

import com.TripLikeMovie.backend.domain.member.presentation.dto.request.ChangeNicknameRequest;
import com.TripLikeMovie.backend.domain.member.presentation.dto.request.ChangePasswordRequest;
import com.TripLikeMovie.backend.domain.member.presentation.dto.request.ChangePasswordVerifyEmailRequest;
import com.TripLikeMovie.backend.domain.member.presentation.dto.request.SendVerificationRequest;
import com.TripLikeMovie.backend.domain.member.presentation.dto.request.VerifyNicknameRequest;

public interface MemberService {

    void validateEmail(SendVerificationRequest sendVerificationRequest);

    void validateNickname(VerifyNicknameRequest verifyNicknameRequest);

    void signUpSuccess(String nickname);

    void changePasswordVerifyEmail(ChangePasswordVerifyEmailRequest changePasswordVerifyEmail);

    void changePassword(ChangePasswordRequest changePasswordRequest);

    void changeNickname(ChangeNicknameRequest changeNicknameRequest);
}
