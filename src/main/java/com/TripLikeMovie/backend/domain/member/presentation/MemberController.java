package com.TripLikeMovie.backend.domain.member.presentation;

import com.TripLikeMovie.backend.domain.credential.presentation.dto.response.AccessTokenAndRefreshTokenDto;
import com.TripLikeMovie.backend.domain.credential.service.CredentialService;
import com.TripLikeMovie.backend.domain.member.domain.vo.MemberInfoVo;
import com.TripLikeMovie.backend.domain.member.presentation.dto.request.ChangeNicknameRequest;
import com.TripLikeMovie.backend.domain.member.presentation.dto.request.ChangePasswordRequest;
import com.TripLikeMovie.backend.domain.member.presentation.dto.request.ChangePasswordVerifyEmailRequest;
import com.TripLikeMovie.backend.domain.member.presentation.dto.request.MemberSignUpRequest;
import com.TripLikeMovie.backend.domain.member.presentation.dto.request.SendVerificationRequest;
import com.TripLikeMovie.backend.domain.member.presentation.dto.request.VerifyEmailCodeRequest;
import com.TripLikeMovie.backend.domain.member.presentation.dto.request.VerifyNicknameRequest;
import com.TripLikeMovie.backend.domain.member.service.MemberService;
import com.TripLikeMovie.backend.domain.member.service.email.EmailService;
import com.TripLikeMovie.backend.global.utils.member.MemberUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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
    private final MemberUtils memberUtils;

    @PostMapping("/send-verification-code")
    public void sendVerificationCode(
        @Valid @RequestBody SendVerificationRequest sendVerificationRequest) {

        log.info("verifying email : {}", sendVerificationRequest.getEmail());
        memberService.validateEmail(sendVerificationRequest); // 이메일이 중복되어 있는지 확인
        emailService.sendSignUpVerificationCode(sendVerificationRequest); // 이메일에 인증코드 전송
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

        emailService.isEmailVerified(signUpRequest.getEmail());
        emailService.deleteEmailVerificationCode(signUpRequest.getEmail());

        memberService.signUpSuccess(signUpRequest.getNickname());

        return credentialService.signUp(signUpRequest);
    }

    @GetMapping
    public MemberInfoVo getMemberInfo() {
        return memberUtils.getMemberFromSecurityContext().getMemberInfo();
    }

    @PostMapping("/change-password/verify-email")
    public void changePasswordVerifyEmail(@Valid @RequestBody ChangePasswordVerifyEmailRequest changePasswordVerifyEmail) {
        memberService.changePasswordVerifyEmail(changePasswordVerifyEmail);
        emailService.sendChangePasswordVerificationCode(changePasswordVerifyEmail.getEmail());
    }

    @PostMapping("/change-password/verify-code")
    public void changePasswordVerifyCode(@Valid @RequestBody VerifyEmailCodeRequest VerifyEmailCodeRequest) {
        emailService.verifyEmailCode(VerifyEmailCodeRequest);
    }

    @PatchMapping("/change-password")
    public void changePassword(@Valid @RequestBody ChangePasswordRequest changePasswordRequest) {
        emailService.isEmailVerified(changePasswordRequest.getEmail());
        memberService.changePassword(changePasswordRequest);
    }

    @PatchMapping("/change-nickname")
    public void changeNickname(@Valid @RequestBody ChangeNicknameRequest changeNicknameRequest) {
        memberService.changeNickname(changeNicknameRequest);
    }

}
