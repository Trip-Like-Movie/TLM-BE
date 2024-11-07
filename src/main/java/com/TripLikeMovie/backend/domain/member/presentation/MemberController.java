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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
    public ResponseEntity<Void> sendVerificationCode(
        @Valid @RequestBody SendVerificationRequest sendVerificationRequest) {
        memberService.validateEmail(sendVerificationRequest); // 이메일이 중복되어 있는지 확인

        emailService.sendVerificationCode(sendVerificationRequest); // 이메일에 인증코드 전송
        return ResponseEntity.ok().build();
    }

    @PostMapping("/verify-code")
    public ResponseEntity<Void> verifyCode(
        @Valid @RequestBody VerifyEmailCodeRequest verifyEmailCodeRequest) {
        emailService.verifyEmailCode(verifyEmailCodeRequest);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/verify-nickname")
    public ResponseEntity<Void> verifyNickname(
        @Valid @RequestBody VerifyNicknameRequest verifyNicknameRequest) {
        memberService.validateNickname(verifyNicknameRequest);
        return ResponseEntity.ok().build();
    }


    @PostMapping
    public ResponseEntity<Void> createMember(
        @Valid @RequestBody MemberSignUpRequest signUpRequest) {

        emailService.isEmailVerified(signUpRequest);

        AccessTokenAndRefreshTokenDto accessTokenAndRefreshTokenDto = credentialService.signUp(
            signUpRequest);
        emailService.deleteEmailVerificationCode(signUpRequest.getEmail());

        memberService.signUpSuccess(signUpRequest.getNickname());

        return ResponseEntity.ok()
            .header("access-token", "Bearer " + accessTokenAndRefreshTokenDto.getAccessToken())
            .header("refresh-token", accessTokenAndRefreshTokenDto.getRefreshToken())
            .build();
    }

}
