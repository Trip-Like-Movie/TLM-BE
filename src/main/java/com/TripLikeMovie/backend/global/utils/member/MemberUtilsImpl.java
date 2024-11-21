package com.TripLikeMovie.backend.global.utils.member;

import com.TripLikeMovie.backend.domain.member.domain.Member;
import com.TripLikeMovie.backend.domain.member.domain.repository.MemberRepository;
import com.TripLikeMovie.backend.global.error.exception.member.MemberNotFoundException;
import com.TripLikeMovie.backend.global.utils.security.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberUtilsImpl implements MemberUtils {

    private final MemberRepository memberRepository;

    @Override
    public Member getMemberByEmail(String email) {
        return memberRepository.findByEmail(email).orElseThrow(() -> MemberNotFoundException.EXCEPTION);
    }

    @Override
    public Member getMemberFromSecurityContext() {
        Integer currentUserId = SecurityUtils.getCurrentUserId();
        return getMemberById(currentUserId);
    }

    private Member getMemberById(Integer id) {
        return memberRepository.findById(id).orElseThrow(() -> MemberNotFoundException.EXCEPTION);
    }
}
