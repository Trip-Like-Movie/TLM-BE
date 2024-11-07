package com.TripLikeMovie.backend.domain.credential.service;

import com.TripLikeMovie.backend.domain.credential.domain.RefreshTokenRedisEntity;
import com.TripLikeMovie.backend.domain.credential.domain.repository.RefreshTokenRedisEntityRepository;
import com.TripLikeMovie.backend.domain.credential.presentation.dto.response.AccessTokenAndRefreshTokenDto;
import com.TripLikeMovie.backend.domain.member.domain.Member;
import com.TripLikeMovie.backend.domain.member.domain.Role;
import com.TripLikeMovie.backend.domain.member.domain.repository.MemberRepository;
import com.TripLikeMovie.backend.domain.member.presentation.dto.request.MemberSignUpRequest;
import com.TripLikeMovie.backend.global.security.JwtTokenProvider;
import com.TripLikeMovie.backend.global.utils.member.MemberUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@AllArgsConstructor
public class CredentialServiceImpl implements CredentialService{

    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenRedisEntityRepository refreshTokenRedisEntityRepository;
    private MemberUtils memberUtils;
    private PasswordEncoder encoder;

    @Transactional(readOnly = true)
    public AccessTokenAndRefreshTokenDto login(Integer memberId){

        Member member = memberUtils.getMemberById(memberId);
        String accessToken = jwtTokenProvider.generateAccessToken(memberId, member.getRole());
        String refreshToken = jwtTokenProvider.generateRefreshToken(memberId, member.getRole());

        RefreshTokenRedisEntity refreshTokenRedisEntity = RefreshTokenRedisEntity.builder()
            .id(memberId.toString())  // memberId를 id로 사용
            .refreshToken(refreshToken)  // 생성된 refreshToken 설정
            .refreshTokenTtl(86400L)  // 1일(86400초) 설정
            .build();

        refreshTokenRedisEntityRepository.save(refreshTokenRedisEntity); // 여기에서 Redis 저장

        return AccessTokenAndRefreshTokenDto.builder()
            .accessToken(accessToken)
            .refreshToken(refreshToken)
            .build();
    }

    public void logoutMember() {
        Member user = memberUtils.getUserFromSecurityContext();
        refreshTokenRedisEntityRepository.deleteById(user.getId().toString());
    }

    public AccessTokenAndRefreshTokenDto signUp(MemberSignUpRequest signUpRequest) {
        String encodedPassword = encoder.encode(signUpRequest.getPassword());

        Member member = Member.builder()
            .email(signUpRequest.getEmail())
            .hashedPassword(encodedPassword)
            .nickname(signUpRequest.getNickname())
            .role(Role.USER)
            .build();

        memberRepository.save(member);

        String refreshToken = jwtTokenProvider.generateRefreshToken(member.getId(), member.getRole());
        String accessToken = jwtTokenProvider.generateAccessToken(member.getId(), member.getRole());

        RefreshTokenRedisEntity refreshTokenRedisEntity = RefreshTokenRedisEntity.builder()
            .id(member.getId().toString())  // memberId를 id로 사용
            .refreshToken(refreshToken)  // 생성된 refreshToken 설정
            .refreshTokenTtl(86400L)  // 1일(86400초) 설정
            .build();

        refreshTokenRedisEntityRepository.save(refreshTokenRedisEntity); // 여기에서 Redis 저장

        return AccessTokenAndRefreshTokenDto.builder()
            .accessToken(accessToken)
            .refreshToken(refreshToken)
            .build();
    }


}
