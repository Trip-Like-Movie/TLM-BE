package com.TripLikeMovie.backend.domain.member.service;

import com.TripLikeMovie.backend.domain.member.presentation.dto.request.SendVerificationRequest;
import com.TripLikeMovie.backend.domain.member.presentation.dto.request.VerifyNicknameRequest;

public interface MemberService {

    void validateEmail(SendVerificationRequest sendVerificationRequest);

    void validateNickname(VerifyNicknameRequest verifyNicknameRequest);

    void signUpSuccess(String nickname);
}
