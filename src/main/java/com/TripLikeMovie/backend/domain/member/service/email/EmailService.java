package com.TripLikeMovie.backend.domain.member.service.email;

import com.TripLikeMovie.backend.domain.member.presentation.dto.request.MemberSignUpRequest;
import com.TripLikeMovie.backend.domain.member.presentation.dto.request.SendVerificationRequest;
import com.TripLikeMovie.backend.domain.member.presentation.dto.request.VerifyEmailCodeRequest;

public interface EmailService {

    void sendVerificationCode(SendVerificationRequest sendVerificationRequest);

    void verifyEmailCode(VerifyEmailCodeRequest verifyEmailCodeRequest);

    void isEmailVerified(MemberSignUpRequest signUpRequest);

    void deleteEmailVerificationCode(String email);

}
