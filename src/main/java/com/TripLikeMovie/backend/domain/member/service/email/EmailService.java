package com.TripLikeMovie.backend.domain.member.service.email;

import com.TripLikeMovie.backend.domain.member.presentation.dto.request.MemberSignUpRequest;
import com.TripLikeMovie.backend.domain.member.presentation.dto.request.SendVerificationRequest;
import com.TripLikeMovie.backend.domain.member.presentation.dto.request.VerifyEmailCodeRequest;

public interface EmailService {

    void sendSignUpVerificationCode(SendVerificationRequest sendVerificationRequest);

    void verifyEmailCode(VerifyEmailCodeRequest verifyEmailCodeRequest);

    void isEmailVerified(String email);

    void deleteEmailVerificationCode(String email);

    void sendChangePasswordVerificationCode(String email);
}
