package com.TripLikeMovie.backend.domain.member.service;

import com.TripLikeMovie.backend.domain.member.domain.repository.MemberRepository;
import com.TripLikeMovie.backend.domain.member.presentation.dto.request.SendVerificationRequest;
import com.TripLikeMovie.backend.domain.member.presentation.dto.request.VerifyNicknameRequest;
import com.TripLikeMovie.backend.global.error.exception.member.DuplicatedEmailException;
import com.TripLikeMovie.backend.global.error.exception.member.DuplicatedNicknameException;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    private final Set<String> nicknameSet = ConcurrentHashMap.newKeySet();

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
    public void signUpSuccess(String nickname) {
        nicknameSet.remove(nickname);
    }


}