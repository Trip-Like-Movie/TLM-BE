package com.TripLikeMovie.backend.domain.member.service;

import com.TripLikeMovie.backend.domain.member.domain.Member;
import com.TripLikeMovie.backend.domain.member.domain.repository.MemberRepository;
import com.TripLikeMovie.backend.domain.member.presentation.dto.request.ChangePasswordRequest;
import com.TripLikeMovie.backend.domain.member.presentation.dto.request.ChangePasswordVerifyEmailRequest;
import com.TripLikeMovie.backend.domain.member.presentation.dto.request.SendVerificationRequest;
import com.TripLikeMovie.backend.domain.member.presentation.dto.request.VerifyNicknameRequest;
import com.TripLikeMovie.backend.global.error.exception.member.DuplicatedEmailException;
import com.TripLikeMovie.backend.global.error.exception.member.DuplicatedNicknameException;
import com.TripLikeMovie.backend.global.error.exception.member.EmailPasswordNotMatchException;
import com.TripLikeMovie.backend.global.error.exception.member.MemberNotFoundException;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    private final Set<String> nicknameSet = ConcurrentHashMap.newKeySet();

    private final PasswordEncoder encoder;

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
}