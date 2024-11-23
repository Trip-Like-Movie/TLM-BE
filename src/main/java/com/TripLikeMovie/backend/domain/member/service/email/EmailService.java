package com.TripLikeMovie.backend.domain.member.service.email;

import com.TripLikeMovie.backend.domain.member.domain.Member;
import com.TripLikeMovie.backend.domain.member.presentation.dto.request.SendVerificationRequest;
import com.TripLikeMovie.backend.domain.member.presentation.dto.request.VerifyEmailCodeRequest;
import com.TripLikeMovie.backend.domain.post.domain.Post;

public interface EmailService {

    void sendSignUpVerificationCode(SendVerificationRequest sendVerificationRequest);

    void verifyEmailCode(VerifyEmailCodeRequest verifyEmailCodeRequest);

    void isEmailVerified(String email);

    void deleteEmailVerificationCode(String email);

    void sendChangePasswordVerificationCode(String email);

    void reportPost(Member member, Post post, String reason);
}
