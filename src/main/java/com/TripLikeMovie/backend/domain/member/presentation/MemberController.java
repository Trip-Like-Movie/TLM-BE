package com.TripLikeMovie.backend.domain.member.presentation;

import com.TripLikeMovie.backend.domain.credential.presentation.dto.response.AccessTokenAndRefreshTokenDto;
import com.TripLikeMovie.backend.domain.credential.service.CredentialService;
import com.TripLikeMovie.backend.domain.member.presentation.dto.request.MemberSignUpRequest;
import com.TripLikeMovie.backend.domain.member.presentation.dto.request.SendVerificationRequest;
import com.TripLikeMovie.backend.domain.member.presentation.dto.request.VerifyEmailCodeRequest;
import com.TripLikeMovie.backend.domain.member.presentation.dto.request.VerifyNicknameRequest;
import com.TripLikeMovie.backend.domain.member.service.MemberService;
import com.TripLikeMovie.backend.domain.member.service.email.EmailService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final EmailService emailService;
    private final MemberService memberService;
    private final CredentialService credentialService;

    @PostMapping("/send-verification-code")
    public void sendVerificationCode(
        @Valid @RequestBody SendVerificationRequest sendVerificationRequest) {

        log.info("verifying email : {}", sendVerificationRequest.getEmail());
        memberService.validateEmail(sendVerificationRequest); // 이메일이 중복되어 있는지 확인
        emailService.sendVerificationCode(sendVerificationRequest); // 이메일에 인증코드 전송
    }

    @PostMapping("/verify-code")
    public void verifyCode(
        @Valid @RequestBody VerifyEmailCodeRequest verifyEmailCodeRequest) {
        emailService.verifyEmailCode(verifyEmailCodeRequest);
    }

    @PostMapping("/verify-nickname")
    public void verifyNickname(
        @Valid @RequestBody VerifyNicknameRequest verifyNicknameRequest) {
        log.info("Verifying nickname {}", verifyNicknameRequest.getNickname());
        memberService.validateNickname(verifyNicknameRequest);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AccessTokenAndRefreshTokenDto signUp(
        @Valid @RequestBody MemberSignUpRequest signUpRequest) {

        emailService.isEmailVerified(signUpRequest);
        emailService.deleteEmailVerificationCode(signUpRequest.getEmail());

        memberService.signUpSuccess(signUpRequest.getNickname());

        return credentialService.signUp(signUpRequest);
    }

}
